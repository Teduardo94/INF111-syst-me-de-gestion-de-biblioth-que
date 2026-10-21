package gestionnaireBibliotheque;

public class Reservation implements Comparable<Reservation> {
    private static int ID_RESERVATION = 1;
    private static int ID_ORDRE_RESERVATION = 1;

    private int id;
    private Livre livre;
    private int idUtilisateur;
    private TypeUtilisateur typeUtilisateur;
    private int ordreReservation;
    private StatutReservation status;

    private static int prochainID(){
        int id = ID_RESERVATION;
        ID_RESERVATION++;
        return id;
    }

    private static int prochainOrdreReservation(){
        int ordreReservation = ID_ORDRE_RESERVATION;
        ID_ORDRE_RESERVATION++;
        return ordreReservation;
    }

    public Reservation(Livre livre, int idUtilisateur, TypeUtilisateur typeUtilisateur) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.typeUtilisateur = typeUtilisateur;
        this.ordreReservation = prochainOrdreReservation();
        this.status = StatutReservation.EN_ATTENTE;
    }

    @Override
    public int compareTo(Reservation obj) {
        int comparerType = obj.typeUtilisateur.compareTo(this.typeUtilisateur);
        if (comparerType != 0)
            return comparerType;
        return Integer.compare(this.ordreReservation, obj.ordreReservation);
    }

    @Override
    public String toString(){
        return String.format("Reservation : %d%n- Livre: %s%n- Utilisateur: %s%n- type: %s%n- Ordre de résérvation: %d%n- Statut: %s",
                id,livre.getTitre(),idUtilisateur,typeUtilisateur,ordreReservation,status);
    }
}
