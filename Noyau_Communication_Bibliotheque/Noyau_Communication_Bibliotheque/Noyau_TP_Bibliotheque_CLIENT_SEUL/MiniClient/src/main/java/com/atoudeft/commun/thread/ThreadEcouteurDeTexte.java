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

package com.atoudeft.commun.thread;
/**
 * Cette classe permet de créer des threads capables de lire continuellement sur un un objet de type Lecteur.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ThreadEcouteurDeTexte extends Thread {
	Lecteur lecteur;

	/**
	 * Construit un thread sur un lecteur
	 * @param lecteur Le lecteur sur lequel le thread va lire
	 */
	public ThreadEcouteurDeTexte(Lecteur lecteur)
	 {
		 this.lecteur = lecteur;
	 }

	/**
	 * Méthode principale du thread. Cette méthode appelle continuellement la méthode lire() du
	 * lecteur (client ou serveur)
	 */
	public void run()
	 {
		while (!interrupted())
		{
			lecteur.lire();
			try
			{
			  Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				break;
			}			
		}
	 }
}
