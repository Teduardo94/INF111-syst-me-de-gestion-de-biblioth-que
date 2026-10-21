package gestionnaireBibliotheque;
import java.util.HashMap;

import java.util.Map;

public class GestionnaireBibliotheque {
    private Map<Integer,Utilisateur> utilisateurs;
    private Map<Integer,Utilisateur> utilisateursAuthentifies;
    private ListeLivres livres;
    private FilePrioriteReservations reservationsEnAttente;

    public GestionnaireBibliotheque() {
        this.utilisateurs = new HashMap<>();
        this.utilisateursAuthentifies = new HashMap<>();
        this.livres = new ListeLivres();
        this.reservationsEnAttente = new FilePrioriteReservations();

        //-----------données ajoutées pour les tests--------------
        this.livres.ajouter(new Livre("Java", "Deitel", "Informatique"));
        this.livres.ajouter(new Livre("CleanCode", "Martin", "Informatique"));
        this.utilisateurs.put(101, new Etudiant(101, "Amine"));
    }

    public enum Erreur{
        AUTHENTICATION_ERROR,
        ALREADY_AUTHENTIFIED_ERROR,
        TOO_MANY_CONNECTIONS_ERROR,
        BAD_ARGUMENT_ERROR,
        COMMAND_ERROR,
        BOOK_NOT_FOUND_ERROR,
        BOOK_NOT_AVAILABLE_ERROR,
        BORROW_LIMIT_ERROR,
        RESERVATION_LIMIT_ERROR,
        BAD_BORROW_ERROR,
        BOOK_ALREADY_AVAILABLE_ERROR
    }

    public String traiterCommande(int idConnexion, String typeCommande, String argument){
        switch (typeCommande){
            case "REGISTER":
                return traiterREGISTER(argument);
            case "ID":
                return traiterID(idConnexion, argument);
            case "BOOKS":
                return traiterBOOKS();
            case "BORROW":
                return traiterBORROW(idConnexion, argument);
            case "RETURN":
                return traiterRETURN(idConnexion, argument);
            case "RESERVE":
                return traiterRESERVE(idConnexion,argument);
            case "INFO":
                return traiterINFO(idConnexion, argument);
            case "PENALTY":
//                return traiterPENALTY(idConnexion,argument);
            case "EXIT":
                return traiterEXIT(idConnexion);
        }
        return"the switch case was not accessed";
    }

    public String traiterID(int idConnexion, String argument){

        int idUtilisateur;
        try {
            idUtilisateur = Integer.parseInt(argument);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        if(utilisateursAuthentifies.containsKey(idConnexion))
            return Erreur.ALREADY_AUTHENTIFIED_ERROR.name();

        if (!utilisateurs.containsKey(idUtilisateur))
            return Erreur.BAD_ARGUMENT_ERROR.name();

        if (utilisateursAuthentifies.containsValue(utilisateurs.get(idUtilisateur)))
            return Erreur.TOO_MANY_CONNECTIONS_ERROR.name();

        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        utilisateursAuthentifies.put(idConnexion,utilisateur);

        return "AUTHORIZED " + idUtilisateur + " " + utilisateur.getNom();
    }

    public String traiterBOOKS(){
        int nbLivres = livres.taille();
        StringBuilder sb = new StringBuilder();
        sb.append("BOOKS ").append(nbLivres);

        for(Livre livre : livres){
            sb.append(" {").append(livre.getIdentifiant())
            .append(" ").append(livre.getTitre())
            .append(" ").append(livre.getAuteur())
            .append(" ").append(livre.getCategorie())
            .append(" ").append(livre.getStatus())
            .append("}");
        }
        return sb.toString();
    }

    public String traiterBORROW(int idConnexion, String argument){
        if(!utilisateursAuthentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();

        if(argument == null)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        String[] parts = argument.trim().split("\\s+");
        if(parts.length < 2)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        int idLivre, jourEmprunt;
        try {
            idLivre = Integer.parseInt(parts[0]);
            jourEmprunt = Integer.parseInt(parts[1]);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        Livre livre = livres.rechercher(idLivre);
        if (livre == null)
            return Erreur.BOOK_NOT_FOUND_ERROR.name();

        if(livre.getStatus() != StatutLivre.DISPONIBLE)
            return Erreur.BOOK_NOT_AVAILABLE_ERROR.name();

        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);

        if(!utilisateur.peutEmprunter())
            return Erreur.BORROW_LIMIT_ERROR.name();

        Emprunt emprunt = new Emprunt(livre, utilisateur.getId(), jourEmprunt);
        utilisateur.ajouterEmpruntEnCours(emprunt);
        livre.setStatus(StatutLivre.EMPRUNTE);

        return "BORROW_OK " + emprunt.getId() + " " + livre.getIdentifiant() + " " + emprunt.getJourRetourPrevu();
    }

    public String traiterRETURN(int idConnexion, String argument){
        if(!utilisateursAuthentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();

        if(argument == null)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        String[] parts = argument.trim().split("\\s+");
        if(parts.length < 2)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        int idEmprunt, jourRetour;
        try {
            idEmprunt = Integer.parseInt(parts[0]);
            jourRetour = Integer.parseInt(parts[1]);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);

        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idEmprunt);
        if(emprunt == null)
            return  Erreur.BAD_BORROW_ERROR.name();

        Livre livre = emprunt.getLivre();

        emprunt.retourner(jourRetour);
        utilisateur.supprimerEmpruntEnCours(idEmprunt);

        Reservation reservation = reservationsEnAttente.retirerPourLivre(livre.getIdentifiant());

        if(reservation == null) {
            livre.setStatus(StatutLivre.DISPONIBLE);
            return "RETURN_OK " + emprunt.getId() + " " + livre.getIdentifiant();
        } else {
            reservation.setStatus(StatutReservation.ATTRIBUEE);
            Emprunt nouvelEmprunt = new Emprunt(livre, reservation.getIdUtilisateur(),jourRetour);
            Utilisateur utilisateurReservation = utilisateurs.get(reservation.getIdUtilisateur());
            utilisateurReservation.ajouterEmpruntEnCours(nouvelEmprunt);
            return "RESERVATION_ASSIGNED " + emprunt.getId() + " " + livre.getIdentifiant();
        }
    }

    public String traiterRESERVE(int idConnexion, String argument){
        if(!utilisateursAuthentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();

        if(argument == null)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        int idLivre;
        try {
            idLivre = Integer.parseInt(argument);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        Livre livre = livres.rechercher(idLivre);
        if (livre == null)
            return Erreur.BOOK_NOT_FOUND_ERROR.name();

        if(livre.getStatus() == StatutLivre.DISPONIBLE)
            return Erreur.BOOK_ALREADY_AVAILABLE_ERROR.name();

        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);

        if(utilisateur.possedeReservationEnAttentePourLivre(idLivre))
            return Erreur.BAD_ARGUMENT_ERROR.name();

        Reservation nouvelleRes = new Reservation(livre,utilisateur.getId(),utilisateur.getTypeUtilisateur());
        if (!utilisateur.ajouterReservation(nouvelleRes))
            return Erreur.RESERVATION_LIMIT_ERROR.name();

        reservationsEnAttente.ajouter(nouvelleRes);
        livre.setStatus(StatutLivre.RESERVE);
        return "RESERVATION_OK " + nouvelleRes.getId() + " " + livre.getIdentifiant();
    }

    public String traiterINFO(int idConnexion, String argument){
        if(!utilisateursAuthentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();

        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);
        if(argument == null){
            StringBuilder sb = new StringBuilder().append("INFO ").append(utilisateur.getEmpruntsEnCours().taille());
            for(Emprunt emprunt : utilisateur.getEmpruntsEnCours()){
                sb.append(" {").append(emprunt.getId()).append(" ")
                        .append(emprunt.getLivre().getIdentifiant()).append(" ")
                        .append(emprunt.getJourEmprunt()).append(" ")
                        .append(emprunt.getJourRetourPrevu()).append(" ")
                        .append(emprunt.getStatus()).append("}");
            }
            return sb.toString();
        }

        int idEmprunt;
        try {
            idEmprunt = Integer.parseInt(argument);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idEmprunt);
        if (emprunt == null)
            return Erreur.BAD_BORROW_ERROR.name();
        return "INFO 1 {"+ emprunt.getId() + " " + emprunt.getLivre().getIdentifiant() + " "
                + emprunt.getJourEmprunt() + " " + emprunt.getJourRetourPrevu() + " "
                + emprunt.getStatus() + "}";
    }

    public String traiterREGISTER(String argument){
        if(argument == null)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        String[] parts = argument.trim().split("\\s+");
        if(parts.length < 3)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        int idUtilisateur;
        try {
            idUtilisateur = Integer.parseInt(parts[0]);
        }catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        if (utilisateurs.containsKey(idUtilisateur))
            return Erreur.BAD_ARGUMENT_ERROR.name();

        String typeUtilisateur = parts[1];
        String nomUtilisateur = parts[2];

        Utilisateur utilisateur;
        if (typeUtilisateur.equalsIgnoreCase("ETUDIANT")){
            utilisateur = new Etudiant(idUtilisateur,nomUtilisateur);
        } else if (typeUtilisateur.equalsIgnoreCase("PERSONNEL")){
            utilisateur = new Personnel(idUtilisateur,nomUtilisateur);
        } else if (typeUtilisateur.equalsIgnoreCase("PROFESSEUR")) {
            utilisateur = new Professeur(idUtilisateur,nomUtilisateur);
        } else {
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        utilisateurs.put(idUtilisateur,utilisateur);
        return "REGISTERED " + idUtilisateur + " " + nomUtilisateur;
    }

    public String traiterEXIT(int idConnexion){
        if(!utilisateursAuthentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();
        utilisateursAuthentifies.remove(idConnexion);
        return "END";
    }

    public String traiterPENALTY(int idConnexion, String argument){
        int idEmprunt, jourActuel, joursRetard;
        double montantPenalite;
        Utilisateur utilisateur = utilisateursAuthentifies.get(idConnexion);

        if(!utilisateursAuthentifies.containsKey(idConnexion)){
            return Erreur.AUTHENTICATION_ERROR.name();
        }

        if (argument == null) {
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        String [] parts = argument.trim().split("\\s+");
        if(parts.length != 2){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        try {
            idEmprunt = Integer.parseInt(parts[0]);
            jourActuel = Integer.parseInt(parts[1]);
        }catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        Emprunt emprunt = utilisateur.rechercherEmpruntEnCours(idEmprunt);

        if(emprunt == null){
            emprunt = utilisateur.rechercherEmpruntTermine(idEmprunt);

            if(emprunt == null){
                return Erreur.BAD_BORROW_ERROR.name();
            }
        }

        joursRetard = emprunt.calculerJoursRetard(jourActuel);
        montantPenalite = utilisateur.calculerPenalite(joursRetard);
        String montantPenaliteFormatte = String.format("%.2f",montantPenalite);

        return "PENALTY " + montantPenaliteFormatte + " " + joursRetard;
    }
}
