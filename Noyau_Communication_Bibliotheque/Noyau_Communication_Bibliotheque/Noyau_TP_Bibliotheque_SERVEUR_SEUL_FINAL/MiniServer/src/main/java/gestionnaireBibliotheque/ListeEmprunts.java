package gestionnaireBibliotheque;

import java.util.ArrayList;
import java.util.Iterator;

public class ListeEmprunts implements IListeEmprunts, Iterable<Emprunt>{
    private ArrayList<Emprunt> liste;

    public ListeEmprunts() {
        this.liste = new ArrayList<Emprunt>();
    }

    @Override
    public boolean ajouter(Emprunt emprunt) {
        if (liste != null){
            liste.add(emprunt);
            return true;
        }
        return false;
    }

    @Override
    public Emprunt supprimer(int idEmprunt) {
        Emprunt empTemp = null;
        for(Emprunt emp : liste){
            if(emp.getId() == idEmprunt){
                empTemp = emp;
                break;
            }
        }
        if(empTemp != null)
            liste.remove(empTemp);
        return empTemp;
    }

    @Override
    public Emprunt rechercher(int idEmprunt) {
        Emprunt empTemp = null;
        for (Emprunt emp : liste){
            if(emp.getId() == idEmprunt){
                empTemp = emp;
                break;
            }
        }
        return empTemp;
    }

    @Override
    public boolean contient(int idEmprunt) {
        boolean contient = false;
        for(Emprunt emp : liste){
            if(emp.getId() == idEmprunt)
                contient = true;
        }
        return contient;
    }

    @Override
    public int taille() {
        if(liste!= null)
            return liste.size();
        return 0;
    }

    @Override
    public boolean estVide() {
        if (liste != null)
            return liste.isEmpty();
        return false;
    }

    @Override
    public Iterator<Emprunt> iterator() {
        return null;
    }

    public String toString(){
        if(liste != null)
            return liste.toString();
        return null;
    }
}
