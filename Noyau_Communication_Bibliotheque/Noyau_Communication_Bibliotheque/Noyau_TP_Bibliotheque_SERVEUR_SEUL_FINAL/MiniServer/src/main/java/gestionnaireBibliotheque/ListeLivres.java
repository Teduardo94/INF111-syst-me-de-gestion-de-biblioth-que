package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ListeLivres implements IListeLivres, Iterable<Livre>{
    private ArrayList<Livre> liste;

    public ListeLivres() {
        this.liste = new ArrayList<Livre>();
    }

    @Override
    public boolean ajouter(Livre livre) {
        if(liste != null)
            return liste.add(livre);
        return false;
    }

    @Override
    public Livre supprimer(int idLivre) {
        if (liste != null && !liste.isEmpty()) {
            for(Livre livre : liste) {
              if (livre.getIdentifiant() == idLivre) {
                  liste.remove(livre);
                  return livre;
              }
            }
        }
        return null;
    }

    @Override
    public Livre rechercher(int idLivre) {
        if (liste != null && !liste.isEmpty()){
            for (Livre livre: liste){
                if(livre.getIdentifiant()==idLivre)
                    return livre;
            }
        }
        return null;
    }

    @Override
    public boolean contient(int idLivre) {
        if(liste != null && !liste.isEmpty()){
            for(Livre livre : liste){
                if (livre.getIdentifiant() == idLivre)
                    return true;
            }
        }
        return false;
    }

    @Override
    public int taille() {
        if(liste != null)
            return liste.size();
        return 0;
    }

    @Override
    public boolean estVide() {
        if(liste != null)
            return liste.isEmpty();
        return true;
    }

    @Override
    public Iterator<Livre> iterator() {
        return new Iterator<Livre>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Livre next() {
                return null;
            }
        };
    }

    @Override
    public String toString(){
        return liste.toString();
    }
}
