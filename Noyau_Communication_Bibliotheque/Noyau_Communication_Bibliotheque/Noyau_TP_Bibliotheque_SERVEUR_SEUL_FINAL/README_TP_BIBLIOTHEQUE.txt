NOYAU CLIENT-SERVEUR FOURNI - INF111
====================================

Auteur original du noyau : Professeur Abdelmoumene (Moumene) Toudeft
Mise a jour du TP par    : Asma Bellili
Date de mise a jour     : 2026-05-13

Contenu fourni
--------------
Ce dossier contient deux projets IntelliJ :

1. MiniServer
   Programme serveur. Dans le TP, il represente le poste principal de gestion
   de la bibliotheque, c'est-a-dire le bibliothecaire.

2. MiniClient
   Programme client. Il permet a un utilisateur d'ecrire des commandes dans
   la console et de les envoyer au serveur. Le client est fourni et ne doit
   pas etre modifie.

Fonctionnement de depart
------------------------
Dans la version fournie, le client peut envoyer une chaine de caracteres au serveur.
Le serveur recoit cette chaine, la convertit en majuscules, puis la renvoie au client.

Exemple :
Client  -> hello
Serveur -> HELLO

Ce comportement sert uniquement a verifier que la communication entre le client et
le serveur fonctionne correctement.

Travail attendu
---------------
Les etudiants devront remplacer ce traitement simple par le traitement des commandes
liees a la gestion de bibliotheque.

Les classes realisees dans les parties 1 et 2 devront etre placees dans le projet MiniServer,
dans le package :

gestionnaireBibliotheque

Le traitement principal devra etre effectue dans la classe :

GestionnaireBibliotheque

Indication importante
---------------------
Le point principal a consulter dans le serveur est :

MiniServer/src/main/java/com/atoudeft/serveur/GestionnaireEvenementServeur.java

C'est dans cette classe que le serveur traite actuellement les commandes recues des clients.
