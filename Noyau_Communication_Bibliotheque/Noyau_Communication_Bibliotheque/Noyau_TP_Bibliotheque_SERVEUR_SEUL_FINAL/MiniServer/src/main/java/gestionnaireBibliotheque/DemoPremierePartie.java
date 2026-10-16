package gestionnaireBibliotheque;

public class DemoPremierePartie {
    public static void main(String[] args) {
        System.out.println("TEST DES INFORMATIONS D'UN LIVRE");
        Livre livre1 = new Livre("Java", "Deitel", "Informatique");
        System.out.println(livre1);

        System.out.println("\nTEST DE LA CRÉATION D'UN EMPRUNT");
        Etudiant etudiant = new Etudiant(101, "Amine");
        Emprunt emprunt = new Emprunt(livre1, etudiant.getId(), 5);
        System.out.println(emprunt);

        System.out.println("\nTEST DU CALCUL DU RETOUR PRÉVU");

        System.out.println("\nTEST DE RETARD");
        int jourTestAvant = 15;
        int jourTestApres = 50;
        System.out.println("Au jour " + jourTestAvant + ", est en retard ? " + emprunt.estEnRetard(jourTestAvant));
        System.out.println("Au jour " + jourTestApres + ", est en retard ? " + emprunt.estEnRetard(jourTestApres));

        System.out.println("\nTEST DU CALCUL DES PÉNALITÉS");
        System.out.println("Jours de retard au jour 50 : " + emprunt.calculerJoursRetard(jourTestApres));

        System.out.println("\nTEST DE LA METHODE RETOURNER");
        emprunt.retourner(40);
        System.out.println("Après un retour au jour 40 :");
        System.out.println(emprunt);

        System.out.println("\nTEST DES LISTES ListeEmprunts & Iterator");
        ListeEmprunts listeEmprunts = new ListeEmprunts();

        Emprunt emprunt2 = new Emprunt(livre1, 102, 10);
        listeEmprunts.ajouter(emprunt);
        listeEmprunts.ajouter(emprunt2);

        System.out.println("Taille de la liste : " + listeEmprunts.taille());
        System.out.println("La liste contient l'emprunt 2 ? " + listeEmprunts.contient(emprunt2.getId()));

        System.out.println("\nParcours de la liste avec l'itérateur (for-each) :");
        for (Emprunt emp : listeEmprunts) {
            System.out.println("- Emprunt ID : " + emp.getId());
        }

        System.out.println("\nTEST DE L'ORDRE DE PRIORITÉ DES RÉSERVATIONS");
    }
}