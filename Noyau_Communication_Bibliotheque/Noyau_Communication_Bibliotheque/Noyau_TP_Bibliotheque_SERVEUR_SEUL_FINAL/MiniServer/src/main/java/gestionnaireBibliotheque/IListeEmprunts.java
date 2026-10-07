package gestionnaireBibliotheque;

public interface IListeEmprunts {
    public boolean ajouter(Emprunt emprunt);
    public Emprunt supprimer(int idEmprunt);
    public Emprunt rechercher(int idEmprunt);
    public boolean contient(int idEmprunt);
    public int taille();
    public boolean estVide();
}
