package controleur;

import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

/**
 * Contrôleur et point d'entrée de l'applicaton
 * 
 * @author emds
 *
 */
public class Controle implements AsyncResponse {

	/**
	 * frame EntreeJeu
	 */
	private EntreeJeu frmEntreeJeu;

	/**
	 * frame Arene
	 */
	private Arene frmArene;

	/**
	 * frame ChoixJoueur
	 */
	private ChoixJoueur frmChoixJoueur;

	/**
	 * port d'ecoute du serveur
	 */
	private static final int PORT = 6666;

	/**
	 * type du jeu : soit serveur, soit client
	 */
	private String TypeJeu;

	/**
	 * Méthode de démarrage
	 * 
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		new Controle();
	}

	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}

	/**
	 * Demande provenant de la vue EntreeJeu
	 * 
	 * @param info information à traiter
	 */
	public void evenementEntreeJeu(String info) {
		if (info.equals("serveur")) {
			this.TypeJeu = "serveur";
			new ServeurSocket(this, PORT);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		} else {
			this.TypeJeu = "client";
			new ClientSocket(this, info, PORT);
		}
	}
	
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		this.frmChoixJoueur.dispose();
		this.frmArene.setVisible(true);
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		switch (ordre) {
		case "connexion":
			if (this.TypeJeu.equals("client")) {
				this.frmEntreeJeu.dispose();
				this.frmChoixJoueur = new ChoixJoueur(this);
				this.frmArene = new Arene();
				this.frmChoixJoueur.setVisible(true);
			}
			break;
		case "réception":
			break;
		case "déconnexion":
			break;
		}
	}
	
}
