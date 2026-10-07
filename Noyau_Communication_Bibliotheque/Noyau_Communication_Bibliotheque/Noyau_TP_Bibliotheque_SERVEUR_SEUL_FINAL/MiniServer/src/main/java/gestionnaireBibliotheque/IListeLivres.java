package gestionnaireBibliotheque;

public interface IListeLivres {
    public boolean ajouter(Livre livre);
    public Livre supprimer(int idLivre);
    public Livre rechercher(int idLivre);
    public boolean contient(int idLivre);
    public int taille();
    public boolean estVie();
}
