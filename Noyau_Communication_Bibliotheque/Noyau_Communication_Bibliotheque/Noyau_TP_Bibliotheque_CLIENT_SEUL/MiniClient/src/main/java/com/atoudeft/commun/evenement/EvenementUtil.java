/*
 * ================================================================
 * CODE FOURNI - INF111
 * Noyau client-serveur
 *
 * Auteur original : Professeur Abdelmoumene (Moumene) Toudeft
 * Mise a jour par : Asma Bellili
 * Date de mise a jour : 2026-05-13
 *
 * Ce fichier fait partie du noyau client-serveur fourni aux etudiants.
 * Le code de communication est deja fonctionnel et ne doit pas etre
 * modifie, sauf indication contraire dans l'enonce du travail pratique.
 * ================================================================
 */

package com.atoudeft.commun.evenement;

/**
 * Classe utilitaire fournissant des méthodes pour manipuler des événements.
 */
public class EvenementUtil {
    /*
      Contructeur privé pour empêcher l'instanciation (inutile) de la classe.
    */
    private EvenementUtil() {
    }

    /**
     * Méthode utilitaire qui extrait à partir d'une chaine de caractères le type et l'argument d'un événement.
     *
     * @param str chaine de caractères décrivant l'événement
     * @return String[] tableau de taille 2 contenant le type et l'argument de l'événement
     */
    public static String[] extraireInfosEvenement(String str) {

        str = str.trim();

        if ("".equals(str))
            return new String[]{"", ""};
        else {
            int i = str.indexOf(' ');
            if (i==-1)
                return new String[]{str, ""};
            else
                return new String[]{str.substring(0,i),str.substring(i).trim()};
        }
    }
}