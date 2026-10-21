package gestionnaireBibliotheque;

public class Etudiant extends Utilisateur{

    public Etudiant(int id, String nom){
        super(id,nom);
    }

    @Override
    public int nombreMaxEmprunts() {
        return 5;
    }

    @Override
    public double calculerPenalite(int joursRetard) {
        return 0.25*joursRetard;
    }

    @Override
    public TypeUtilisateur getTypeUtilisateur() {
        return TypeUtilisateur.ETUDIANT;
    }
}
