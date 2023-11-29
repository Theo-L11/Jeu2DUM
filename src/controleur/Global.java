/**
 * 
 */
package controleur;

/**
 * Global contient les constantes du programme
 * @author emds
 *
 */
public interface Global {
	
	/**
	 * N° du port d'écoute du serveur
	 */
	int PORT = 6666;
	/**
	 * Nombre de personnages différents
	 */
	int NBPERSOS = 3;
	/**
	 * Caractère de séparation dans un chemin de fichiers
	 */
	String CHEMINSEPARATOR = "/";
	/**
	 * Chemin du dossier des images de fonds
	 */
	String CHEMINFONDS = "fonds"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier de l'image de la boule
	 */
	String CHEMINBOULES = "boules"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier de l'image du mur
	 */
	String CHEMINMURS = "murs"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier des images des personnages
	 */
	String CHEMINPERSONNAGES = "personnages"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier des sons
	 */
	String CHEMINSONS = "sons"+CHEMINSEPARATOR;
	/**
	 * Chemin de l'image de fond de la vue ChoixJoueur
	 */
	String FONDCHOIX = CHEMINFONDS+"fondchoix.jpg";
	/**
	 * Chemin de l'image de fond de la vue Arene
	 */
	String FONDARENE = CHEMINFONDS+"fondarene.jpg";
	/**
	 * Extension des fichiers des images des personnages
	 */
	String EXTFICHIERPERSO = ".gif";
	/**
	 * Début du nom des images des personnages
	 */
	String PERSO = "perso";
	/**
	 * Chemin de l'image de la boule
	 */
	String BOULE = CHEMINBOULES+"boule.gif";
	/**
	 * Chemin de l'image du mur
	 */
	String MUR = CHEMINMURS+"mur.gif";
	/**
	 * état marche du personnage
	 */
	String MARCHE = "marche";
	/**
	 * état touché du personnage
	 */
	String TOUCHE = "touche";
	/**
	 * état mort du personnage
	 */
	String MORT = "mort";
	/**
	 * Caractère de séparation dans les chaines transférées
	 */
	String STRINGSEPARE = "~";
	/**
	 * Message "connexion" envoyé par la classe Connection
	 */
	String CONNEXION = "connexion";
	/**
	 * Message "réception" envoyé par la classe Connection
	 */
	String RECEPTION = "réception";
	/**
	 * Message "déconnexion" envoyé par la classe Connection
	 */
	String DECONNEXION = "déconnexion";
	/**
	 * Message "pseudo" envoyé pour la création d'un joueur
	 */
	String PSEUDO = "pseudo";
	/**
	 * vie de départ pour tous les joueurs
	 */
	int MAXVIE = 10 ;
	/**
	 * gain de points de vie lors d'une attaque
	 */
	int GAIN = 1 ; 
	/**
	 * perte de points de vie lors d'une attaque
	 */
	int PERTE = 2 ;
	/**
	 * largeur de la zone de jeu de l'arene
	 */
	int LARGEURARENE = 800;
	/**
	 * hauteur de la zone de jeu de l'arene
	 */
	int HAUTEURARENE = 600;
	/**
	 * largeur d'un mur
	 */
	int LARGEURMUR = 34;
	/**
	 * hauteur d'un mur
	 */
	int HAUTEURMUR = 35;
	/**
	 * largeur d'un perso
	 */
	int LARGEURPERSO = 39;
	/**
	 * hauteur d'un perso
	 */
	int HAUTEURPERSO = 44;
	/**
	 * hauteur du message
	 */
	int HAUTEURMESSAGE = 8;
	/**
	 * orientation du personnage vers la gauche
	 */
	int GAUCHE = 0;
	/**
	 * orientation du personnage vers la droite
	 */
	int DROITE = 1;
	/**
	 * nombre de murs
	 */
	int NBMURS = 20;
	/**
	 * ordre d'ajout mur
	 */
	String AJOUTMUR = "ajout mur";
	/**
	 * ordre serveur
	 */
	String SERVEUR = "serveur";
	/**
	 * ordre ajout panel murs
	 */
	String AJOUTPANELMURS = "ajout panel murs";
	/**
	 * ordre ajout label jeu
	 */
	String AJOUTLABELJEU = "ajout jlabel jeu";
	/**
	 * ordre modif panel jeu
	 */
	String MODIFPANELJEU = "modif panel jeu";
	/**
	 * ordre tchat
	 */
	String TCHAT = "tchat";
	/**
	 * ordre d'ajouter une phrase
	 */
	String AJOUTPHRASE = "ajout phrase";
	/**
	 * ordre modif tchat
	 */
	String MODIFTCHAT = "modif tchat";
	/**
	 * s'agit d'un jeu client
	 */
	String CLIENT = "client";
	/**
	 * etoiles d'ouverture et fermeture de phrase
	 */
	String ETOILES = "***";
	/**
	 * premier message quand perso se connecte
	 */
	String VIENTDESECO = " vient de se connecter ";
	/**
	 * ordre action
	 */
	String ACTION = "action";
	/**
	 * nombre de pas pour un deplacement (en pixel)
	 */
	int NBPAS = 10;
	/**
	 * nombre d'étapes (d'images) pour donner l'impresson de marche
	 */
	int NBETAPESMARCHE = 4;
	/**
	 * nombres d'etapes (d'images) pour donner l'impression qu'un personnage est touché
	 */
	int NBETAPESTOUCHE = 2;
	/**
	 * nombres d'étapes (d'image) pour donner l'impression que le personnage meurt
	 */
	int NBETAPESMORT = 2;
	/**
	 * largeur d'une boule
	 */
	int LARGEURBOULE = 17;
	/**
	 * hauteur d'une boule
	 */
	int HAUTEURBOULE = 17;
}