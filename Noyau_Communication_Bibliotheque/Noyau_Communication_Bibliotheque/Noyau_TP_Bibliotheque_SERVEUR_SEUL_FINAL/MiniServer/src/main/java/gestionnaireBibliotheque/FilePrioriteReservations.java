package gestionnaireBibliotheque;

import java.util.Iterator;
import java.util.PriorityQueue;

public class FilePrioriteReservations {
    private PriorityQueue<Reservation> queue;

    public FilePrioriteReservations() {
        this.queue = new PriorityQueue<Reservation>();
    }

    public PriorityQueue<Reservation> getQueue() {
        return queue;
    }

    public boolean ajouter(Reservation reservation){
        if(queue != null) {
            queue.add(reservation);
            return true;
        }
        else
            return false;
    }

    public Reservation consulter(){
        return queue.peek();
    }

    public Reservation retirer(){
        if (queue != null && !queue.isEmpty()){
            Reservation r = queue.peek();
            queue.remove(r);
            return r;
        }
        return null;
    }

    public boolean estVide(){
        return queue.isEmpty();
    }

    public int taille(){
        if(queue != null && !queue.isEmpty())
            return queue.size();
        else
            return 0;
    }

    @Override
    public String toString(){
        String liste;
        if(queue == null || queue.isEmpty()){
            return "La liste est vide";
        }else{
            return queue.toString();
        }
    }

    public Reservation retirerPourLivre(int idLivre){
        if(queue == null || queue.isEmpty())
            return null;

        for(Reservation reservation: queue){
            if (reservation.getLivre().getIdentifiant() == idLivre
            && reservation.getStatus() == StatutReservation.EN_ATTENTE){
                queue.remove(reservation);
                return reservation;
            }
        }
        return null;
    }
}
