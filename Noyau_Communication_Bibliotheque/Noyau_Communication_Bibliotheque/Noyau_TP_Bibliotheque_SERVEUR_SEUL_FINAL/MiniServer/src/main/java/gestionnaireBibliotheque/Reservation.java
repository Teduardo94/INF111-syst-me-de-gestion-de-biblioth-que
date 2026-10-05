package gestionnaireBibliotheque;

public class Reservation {
    private static int ID_RESERVATION = 1;

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



}
