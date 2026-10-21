package gestionnaireBibliotheque;

import java.util.Locale;
import java.util.Scanner;

public class DemoDeuxiemePartie {
    static Scanner clavier = new Scanner(System.in);

    public static void main(String[] args) {
        clavier.useLocale(Locale.ENGLISH);
        GestionnaireBibliotheque gestionnaire = new GestionnaireBibliotheque();
        int idConnexion = 1; // ID de connexion unique pour ce client de test



        while (true) {
            System.out.print("\nEntrez votre commande : ");
            String ligne = clavier.nextLine().trim();

            if (ligne.equalsIgnoreCase("q")) {
                System.out.println("Fin de la démonstration.");
                break;
            }

            if (ligne.isEmpty()) {
                continue;
            }

            String typeCommande;
            String argument = null;

            int espaceIndex = ligne.indexOf(' ');
            if (espaceIndex != -1) {
                typeCommande = ligne.substring(0, espaceIndex).toUpperCase();
                argument = ligne.substring(espaceIndex + 1).trim();
            } else {
                typeCommande = ligne.toUpperCase();
            }

            String reponse = gestionnaire.traiterCommande(idConnexion, typeCommande, argument);
            System.out.println("Réponse du serveur -> " + reponse);
        }
    }

    private static void menu(){
        System.out.println("Commandes disponibles : ");
        System.out.println(" - REGISTER <id> <ETUDIANT/PERSONNEL/PROFESSEUR> <nom>");
        System.out.println(" - ID <id>");
        System.out.println(" - BOOKS");
        System.out.println(" - BORROW <idLivre> <jour>");
        System.out.println(" - RETURN <idEmprunt> <jour>");
        System.out.println(" - RESERVE <idLivre>");
        System.out.println(" - INFO [idEmprunt]");
        System.out.println(" - PENALTY <idEmprunt> <jourActuel>");
        System.out.println(" - EXIT");
        System.out.println(" - q (Quitter la démo)\n");
    }
}