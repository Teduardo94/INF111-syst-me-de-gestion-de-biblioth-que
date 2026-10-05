/*
 * ================================================================
 * CODE FOURNI - INF111
 * Noyau client-serveur
 *
 * Auteur  : Professeur Abdelmoumene  Toudeft
 * Documenté  par  : Asma Bellili
 *
 *
 * Ce fichier fait partie du noyau client-serveur fourni aux etudiants.
 * Le code de communication est deja fonctionnel et ne doit pas etre
 * modifie, sauf indication contraire dans l'enonce du travail pratique.
 * ================================================================
 */

package com.atoudeft.serveur;

import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

/**
 * Cette classe repr�sente un gestionnaire d'�v�nement d'un serveur. Lorsqu'un serveur re�oit un texte d'un client,
 * il cr�e un �v�nement � partir du texte re�u et alerte ce gestionnaire qui r�agit en g�rant l'�v�nement.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'�v�nements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire g�re des �v�nements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * M�thode de gestion d'�v�nements. Cette m�thode contiendra le code qui g�re les r�ponses obtenues d'un client.
     *
     * @param evenement L'�v�nement � g�rer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String reponse;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            if ("EXIT".equals(evenement.getType())) {

                cnx.envoyer("END.");
                cnx.close();
            } else {
                /*
                 * ------------------------------------------------------------
                 * TRAITEMENT FOURNI AU DEPART
                 * ------------------------------------------------------------
                 * Dans le noyau fourni, le serveur ne fait pas encore de vraie
                 * gestion de bibliotheque. Il se contente de reprendre le texte
                 * recu du client, de le convertir en majuscules, puis de le
                 * renvoyer au client.
                 *
                 * Exemple :
                 * Client  -> hello
                 * Serveur -> HELLO
                 *
                 * Ce traitement simple permet seulement de verifier que la
                 * communication entre le client et le serveur fonctionne bien.
                 *
                 * Dans le TP, c'est principalement ici que le traitement devra
                 * etre remplace par un appel au gestionnaire de bibliotheque
                 * que vous aurez programme dans le package :
                 *
                 *     gestionnaireBibliotheque
                 *
                 * Le serveur devra alors transmettre la commande recue au
                 * GestionnaireBibliotheque afin d'obtenir une vraie reponse,
                 * par exemple : AUTHORIZED, BORROW_OK, RETURN_OK, etc.
                 * ------------------------------------------------------------
                 */
                reponse = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                cnx.envoyer(reponse);
            }
        }
    }
}
