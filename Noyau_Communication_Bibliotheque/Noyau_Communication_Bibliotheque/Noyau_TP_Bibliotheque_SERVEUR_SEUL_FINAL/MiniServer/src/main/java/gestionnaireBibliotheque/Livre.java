package gestionnaireBibliotheque;

public class Livre {
    private static int EMPRUNT_MAX = 40;
    private static int ID_LIVRE = 1;

    private String titre;
    private String auteur;
    private String categorie;
    private int identifiant;
    private StatutLivre status;

    private static int prochainID(){
        int id = ID_LIVRE;
        ID_LIVRE++;
        return id;
    }

    public Livre(String titre, String auteur, String categorie) {
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.identifiant = prochainID();
        this.status = StatutLivre.DISPONIBLE;
    }

    public int getEmpruntMax(){
        return EMPRUNT_MAX;
    }

    @Override
    public String toString(){
        return String.format("titre: %s%n- auteur: %s%n- catégorie: %s%n- identifiant: %d%n- status: %s",
                titre, auteur, categorie, identifiant,status);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || getClass() != obj.getClass())
            return false;
        Livre livre = (Livre) obj;
        return this.identifiant == livre.identifiant;
    }
}
