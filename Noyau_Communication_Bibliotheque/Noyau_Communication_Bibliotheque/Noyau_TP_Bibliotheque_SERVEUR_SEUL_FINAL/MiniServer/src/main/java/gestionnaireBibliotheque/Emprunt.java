package gestionnaireBibliotheque;

public class Emprunt {
    private static int ID_EMPRUNT = 1;

    private int id;
    private Livre livre;
    private int idUtilisateur;
    private int jourEmprunt;
    private int jourRetourPrevu;
    private StatutEmprunt status;

    private static int prochainID(){
        int id = ID_EMPRUNT;
        ID_EMPRUNT++;
        return id;
    }

    public Emprunt(Livre livre, int idUtilisateur, int jourEmprunt) {
        this.id = prochainID();
        this.livre = livre;
        this.idUtilisateur = idUtilisateur;
        this.jourEmprunt = jourEmprunt;
        this.jourRetourPrevu = jourEmprunt + livre.getEmpruntMax();
        this.status = StatutEmprunt.EN_COURS;
    }

    public boolean estEnRetard(int jourActuel){
        return jourRetourPrevu > jourActuel;
    }

    public int calculerJoursRetard(int jourActuel){
        if (!estEnRetard(jourActuel))
            return 0;
        return jourActuel - jourRetourPrevu;
    }

    public void retourner(int jourRetour){
        if(!estEnRetard(jourRetour))
            this.status = StatutEmprunt.RETOURNE;
        else
            this.status = StatutEmprunt.EN_RETARD;
        this.jourRetourPrevu = jourRetour;
    }

    @Override
    public String toString(){
        return String.format("id: %d%n- livre: %s%n- utilisateur: %d%n- " +
                        "jour de l'emprunt: %d%n- jour de retour prévu: %d%n- status: %s",
                id,livre.getTitre(),idUtilisateur,jourEmprunt,jourRetourPrevu,status);
    }
}
