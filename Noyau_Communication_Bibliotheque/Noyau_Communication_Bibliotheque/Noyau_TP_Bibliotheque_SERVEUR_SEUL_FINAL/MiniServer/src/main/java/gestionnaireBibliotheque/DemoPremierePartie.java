package gestionnaireBibliotheque;
/*
    TP#1 (Système de gestion de bibliothèque) réalisé dans le cadre du cours INF111
    Code écrit par : Torres Gomez, Eduardo Eugenio et El-Agha, Emmanuel
    Travail présenté à : Mme Asma Bellili
    Lien vers le projet GitHub : https://github.com/Teduardo94/INF111-syst-me-de-gestion-de-biblioth-que
 */
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
        Reservation resEtudiant1 = new Reservation(livre1,101,TypeUtilisateur.ETUDIANT);
        Reservation resEtudiant2 = new Reservation(livre1,102,TypeUtilisateur.ETUDIANT);
        Reservation resPersonnel1 = new Reservation(livre1,201,TypeUtilisateur.PERSONNEL);
        Reservation resPersonnel2 = new Reservation(livre1,202,TypeUtilisateur.PERSONNEL);
        Reservation resProfesseur1 = new Reservation(livre1,301,TypeUtilisateur.PROFESSEUR);
        Reservation resProfesseur2 = new Reservation(livre1,302,TypeUtilisateur.PROFESSEUR);

        System.out.println("Résultats positifs : " +
                "\n- Etudiant et Personnel : " + resEtudiant2.compareTo(resPersonnel1) +
                "\n- Etudiant et Professeur : " + resEtudiant2.compareTo(resProfesseur1) +
                "\n- Personnel et Professeur : " + resPersonnel1.compareTo(resProfesseur1) +
                "\n- Etudiant récent et Etudiant ancien : " + resEtudiant2.compareTo(resEtudiant1) +
                "\n- Personnel récent et Personnel ancien : " + resPersonnel2.compareTo(resPersonnel1) +
                "\n- Professeur récent et Professeur ancien : " + resProfesseur2.compareTo(resProfesseur1));

        System.out.println("Résultats négatifs : " +
                "\n- Personnel et Etudiant : " + resPersonnel1.compareTo(resEtudiant2) +
                "\n- Professeur et Etudiant : " + resProfesseur1.compareTo(resEtudiant2) +
                "\n- Professeur et Personnel : " + resProfesseur1.compareTo(resPersonnel1) +
                "\n- Personnel ancien et Personnel récent : " + resPersonnel1.compareTo(resPersonnel2) +
                "\n- Etudiant ancien et Etudiant récent : " + resEtudiant1.compareTo(resEtudiant2) +
                "\n- Professeur ancien et Professeur récent : " + resProfesseur1.compareTo(resProfesseur2));

        System.out.println("Résultats nuls : " +
                "\n- Personnel1 : " + resPersonnel1.compareTo(resPersonnel1) +
                "\n- Etudiant2 : " + resEtudiant2.compareTo(resEtudiant2) +
                "\n- Professeur1 : " + resProfesseur1.compareTo(resProfesseur1));

        FilePrioriteReservations file = new FilePrioriteReservations();
        file.ajouter(resEtudiant2);
        file.ajouter(resProfesseur2);
        file.ajouter(resPersonnel2);
        file.ajouter(resEtudiant1);
        file.ajouter(resProfesseur1);
        file.ajouter(resPersonnel1);

        System.out.println("\nOrdre de sortie de la file :");
        while (!file.estVide()){
            System.out.println(file.retirer());
        }
    }
}