package gestionnaireBibliotheque;

public class DemoPremierePartie {
    public static void main(String[] args) {
        Livre livre = new Livre("Java", "Deitel", "Informatique");
        Etudiant etudiant = new Etudiant(101,"Amine");
        Emprunt emprunt = new Emprunt(livre, etudiant.getId(),5);
        System.out.println(emprunt);
        System.out.println("Jours de retard : " + emprunt.calculerJoursRetard(50));
    }
}
