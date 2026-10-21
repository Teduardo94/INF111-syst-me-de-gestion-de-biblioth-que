package gestionnaireBibliotheque;

import jdk.jfr.events.ErrorThrownEvent;

import javax.swing.plaf.BorderUIResource;
import java.util.Locale;
import java.util.Map;

public class GestionnaireBibliotheque {
    private Map<Integer,Utilisateur> utilisateurs;
    private Map<Integer,Utilisateur> utilisateursIdentifies;
    private ListeLivres livres;
    private FilePrioriteReservations reservationsEnAttente;

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
                break;
            case "INFO":
                break;
            case "PENALTY":
                break;
            case "EXIT":
                return traiterEXIT(idConnexion);
            default:
                return "";
        }
        return"";
    }

    public String traiterID(int idConnexion, String argument){

        int idUtilisateur;
        try {
            idUtilisateur = Integer.parseInt(argument);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }

        if(utilisateursIdentifies.containsKey(idConnexion))
            return Erreur.ALREADY_AUTHENTIFIED_ERROR.name();

        if (!utilisateurs.containsKey(idUtilisateur))
            return Erreur.BAD_ARGUMENT_ERROR.name();

        if (utilisateursIdentifies.containsValue(utilisateurs.get(idUtilisateur)))
            return Erreur.TOO_MANY_CONNECTIONS_ERROR.name();

        Utilisateur utilisateur = utilisateurs.get(idUtilisateur);
        utilisateursIdentifies.put(idConnexion,utilisateur);

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
        if(!utilisateursIdentifies.containsKey(idConnexion))
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

        Utilisateur utilisateur = utilisateursIdentifies.get(idConnexion);

        if(!utilisateur.peutEmprunter())
            return Erreur.BORROW_LIMIT_ERROR.name();

        Emprunt emprunt = new Emprunt(livre, utilisateur.getId(), jourEmprunt);
        utilisateur.ajouterEmpruntEnCours(emprunt);
        livre.setStatus(StatutLivre.EMPRUNTE);

        return "BORROW_OK " + emprunt.getId() + " " + livre.getIdentifiant() + " " + emprunt.getJourRetourPrevu();
    }

    public String traiterRETURN(int idConnexion, String argument){
        if(!utilisateursIdentifies.containsKey(idConnexion))
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

        Utilisateur utilisateur = utilisateursIdentifies.get(idConnexion);

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
        if(!utilisateursIdentifies.containsKey(idConnexion))
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

        Utilisateur utilisateur = utilisateursIdentifies.get(idConnexion);
        if(!utilisateur.peutEmprunter())
            return Erreur.BORROW_LIMIT_ERROR.name();

        if(utilisateur.possedeReservationEnAttentePourLivre(idLivre))
            return Erreur.BAD_ARGUMENT_ERROR.name();

        Reservation nouvelleRes = new Reservation(livre,utilisateur.getId(),utilisateur.getTypeUtilisateur());
        reservationsEnAttente.ajouter(nouvelleRes);
        return "RESERVATION_OK" + nouvelleRes.getId() + " " + livre.getIdentifiant();
    }

    public String traiterINFO(int idConnexion, String argument){
        if(!utilisateursIdentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();

        if(argument == null)
            return Erreur.BAD_ARGUMENT_ERROR.name();

        int idEmprunt;
        try {
            idEmprunt = Integer.parseInt(argument);
        } catch (Exception e){
            return Erreur.BAD_ARGUMENT_ERROR.name();
        }
        return "";
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
        if(!utilisateursIdentifies.containsKey(idConnexion))
            return Erreur.AUTHENTICATION_ERROR.name();
        utilisateursIdentifies.remove(idConnexion);
        return "END";
    }

}
