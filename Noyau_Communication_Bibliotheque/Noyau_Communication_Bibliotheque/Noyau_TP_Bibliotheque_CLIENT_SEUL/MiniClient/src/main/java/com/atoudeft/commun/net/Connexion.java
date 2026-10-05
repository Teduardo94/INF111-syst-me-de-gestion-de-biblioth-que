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

package com.atoudeft.commun.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Cette classe représente un point de connexion d'un client vers un serveur ou d'un serveur vers un client.
 * Encapsule le socket utilisé pour la connexion ainsi que les flux de caractères pour envoyer et recevoir du texte.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Connexion {

	private Socket 				socket;
	private PrintWriter 		os;
	private BufferedInputStream is;
	/**
	 * Construit une connexion sur un socket, initialisant les flux de caractères utilisés par le socket.
	 *
	 * @param s Socket Le socket sur lequel la connexion est créée
	 */
	public Connexion(Socket s)
	{
		try
		{
		  socket = s;
		  is = new BufferedInputStream(socket.getInputStream());
		  os = new PrintWriter(socket.getOutputStream());			
		}
		catch(IOException e)
		{		
		}
	}
	/**
	 * Vérifie si du texte est arrivé sur la connexion et le retourne. Retourne la chaine vide s'il n'y a pas de texte.
	 *
	 * @return String le texte reçu, ou la chaine vide, si aucun texte n'est arrivé.
	 */
	public String getAvailableText()
	{
		String t = "";
		try
		 {
			byte buf[] = new byte[2000];	//buffer de lecture
						
			if (is.available()<=0)
				return "";
			//Lire le inputstream
			is.read(buf);
			t = (new String(buf)).trim();
			//System.out.println(texte);
			//Effacer le buffer
			buf=null;
		}
		catch(IOException e) 
		{
		}			
		return t;
	}
	/**
	 * Envoie un texte sur la connexion
	 *
	 * @param texte String texte envoyé
	 */
	public void envoyer(String texte)
	 {
		os.print(texte);
		os.flush();
	 }
	/**
	 * Ferme la connexion en fermant le socket et les flux utilisés.
	 *
	 * @return true si la connexion a été fermée correctement et false, sinon.
	 */
	public boolean close()
	{
		try
		{
		  //envoyer("Connexion closed !");
		  is.close();
		  os.close();			
		  socket.close();
		}
		catch(IOException e)
		{
			return false;
		}
		return true;
	}
}