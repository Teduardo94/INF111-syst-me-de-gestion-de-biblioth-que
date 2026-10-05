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

package com.atoudeft.client;

/**
 * Informations sur un serveur, utilisées par défaut par un client.
 */
public interface Config {
    /**
     * Adresse IP du serveur.
     */
    String ADRESSE_SERVEUR = "127.0.0.1";
    /**
     * Port d'écoute du serveur.
     */
    int PORT_SERVEUR = 8888;
}
