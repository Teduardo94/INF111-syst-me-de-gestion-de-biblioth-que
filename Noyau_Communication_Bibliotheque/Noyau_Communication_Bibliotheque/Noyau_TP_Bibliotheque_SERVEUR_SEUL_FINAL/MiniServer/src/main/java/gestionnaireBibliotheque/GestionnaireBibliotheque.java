package gestionnaireBibliotheque;

import java.util.Map;

public class GestionnaireBibliotheque {
    private Map<Integer,Utilisateur> utilisateurs;
    private Map<Integer,Utilisateur> utilisateursIdentifies;
    private ListeLivres livres;
    private FilePrioriteReservations reservationsEnAttente;

    public String traiterCommande(int idConnexion, String typeCommande, String argument){
        switch (typeCommande){
            case "REGISTER":
                break;
            case "ID":
                break;
            case "BOOKS":
                break;
            case "BORROW":
                break;
            case "RETURN":
                break;
            case "RESERVE":
                break;
            case "INFO":
                break;
            case "PENALTY":
                break;
            case "EXIT":
                break;
            default:
                return "";
        }
        return"";
    }

    public String traiterID(int idConnexion, String argument){
        return "";
    }


}
