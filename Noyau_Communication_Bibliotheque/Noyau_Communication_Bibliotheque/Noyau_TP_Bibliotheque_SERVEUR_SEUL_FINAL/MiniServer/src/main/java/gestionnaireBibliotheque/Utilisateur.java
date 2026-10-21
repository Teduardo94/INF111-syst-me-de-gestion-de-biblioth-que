package gestionnaireBibliotheque;

public abstract class Utilisateur {
    private int id;
    private String nom;
    private ListeEmprunts empruntsEnCours;
    private ListeEmprunts empruntsTermines;
    private FilePrioriteReservations reservations;
    private static final int MAX_RESERVATIONS = 10;

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.empruntsEnCours = new ListeEmprunts();
        this.empruntsTermines = new ListeEmprunts();
        this.reservations = new FilePrioriteReservations();
    }

    public String getNom() {
        return nom;
    }

    public ListeEmprunts getEmpruntsEnCours() {
        return empruntsEnCours;
    }

    public void ajouterEmpruntEnCours(Emprunt emprunt){
        empruntsEnCours.ajouter(emprunt);
    }

    public void ajouterEmpruntTermine(Emprunt emprunt){
        empruntsTermines.ajouter(emprunt);
    }

    public Emprunt supprimerEmpruntEnCours(int idEmprunt){
        if(empruntsEnCours.contient(idEmprunt)){
            Emprunt emprunt = empruntsEnCours.rechercher(idEmprunt);
            empruntsEnCours.supprimer(idEmprunt);
            return emprunt;
        } else {
            return null;
        }
    }

    public Emprunt rechercherEmpruntEnCours(int idEmprunt){
        if(empruntsEnCours.contient(idEmprunt)){
            Emprunt emprunt = empruntsEnCours.rechercher(idEmprunt);
            return emprunt;
        }else{
            return null;
        }
    }

    public Emprunt rechercherEmpruntTermine(int idEmprunt){
        if(empruntsTermines.contient(idEmprunt)){
            Emprunt emprunt = empruntsTermines.rechercher(idEmprunt);
            return emprunt;
        }else{
            return null;
        }
    }

    public boolean aDesEmpruntsEnCours(){
        if(empruntsEnCours.estVide()){
            return false;
        }else{
            return true;
        }
    }

    public abstract int nombreMaxEmprunts();

    public boolean peutEmprunter(){
        if(empruntsEnCours.taille() < nombreMaxEmprunts()){
            return true;
        } else {
            return false;
        }
    }

    public boolean possedeReservationEnAttentePourLivre(int idLivre){
        for(Reservation res : reservations.getQueue()){
            if(res.getLivre().getIdentifiant() == idLivre && res.getStatus() == StatutReservation.EN_ATTENTE){
                return true;
            }
        }

        return false;
    }

    public boolean ajouterReservation(Reservation reservation){
        if(reservations.taille() < MAX_RESERVATIONS){
            reservations.ajouter(reservation);
            return true;
        }else {
            return false;
        }

    }

    public int getId() {
        return id;
    }

    public abstract double calculerPenalite(int joursRetard);

    public abstract TypeUtilisateur getTypeUtilisateur();

    @Override
    public String toString() {
        return "Utilisateur{" +
                "type : '" + getTypeUtilisateur() + '\'' +
                ", id : '" + id + '\'' +
                ", nom : '" + nom + '\'' +
                ", nombre d'emprunts en cours : '" + empruntsEnCours.taille() + '\'' +
                ", nombre d'emprunts termines : '" + empruntsTermines.taille() + '\'' +
                '}';
    }
}
