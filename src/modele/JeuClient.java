package modele;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu implements Global {

	private Connection connection;

	/**
	 * Controleur
	 */
	public JeuClient(Controle controle) {
		super.controle = controle;
	}

	@Override
	public void deconnexion() {

	}

	@Override
	public void reception(Connection connection, Object info) {
	}

	/**
	 * Envoi d'une information vers le serveur fais appel une fois à l'envoi dans la
	 * classe Jeu
	 */
	public void envoi(String infoJoueur) {
		super.envoi(this.connection, infoJoueur);
	}

	@Override
	public void connexion(Connection connection) {
		this.connection = connection;
	}

}
