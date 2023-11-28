package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 *
 */
public class JeuServeur extends Jeu implements Global {

	/**
	 * permet de connatre les persos connectés
	 */
	private Connection connection;
	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>();
	/**
	 * Collection de joueurs
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();

	/**
	 * Constructeur
	 * @param controle instance de la classe Controle du controleur
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}

	/**
	 * ajoute un label dans le jeu
	 * @param info JLabel a ajouter
	 */
	public void ajoutJLabelJeuArene(JLabel info) {
		this.controle.evenementJeuServeur(AJOUTLABELJEU, info);
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String) info).split(STRINGSEPARE);
		String ordre = infos[0];
		switch (ordre) {
		case PSEUDO:
			this.controle.evenementJeuServeur(AJOUTPANELMURS, connection);
			String pseudo = infos[1];
			int numPerso = Integer.parseInt(infos[2]);
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			String premierMessage = ETOILES + pseudo + VIENTDESECO + ETOILES;
			this.controle.evenementJeuServeur(AJOUTPHRASE, premierMessage);
			break;
		case TCHAT:
			String phrase = infos[1];
			phrase = this.lesJoueurs.get(connection).getPseudo()+" > "+phrase;
			this.controle.evenementJeuServeur(AJOUTPHRASE, phrase);
			break;
		case ACTION:
			Integer action = Integer.parseInt(infos[1]);
			this.lesJoueurs.get(connection).action(action, this.lesJoueurs.values(), this.lesMurs);
		}
	}

	@Override
	public void deconnexion() {
	}

	/**
	 * Envoi d'une information vers tous les clients fais appel plusieurs fois à
	 * l'envoi de la classe Jeu
	 * @param info information a traité
	 */
	public void envoi(Object info) {
		for(Connection connection : this.lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}		
	}

	/**
	 * Envoi du panel de jeu à tous les joueurs
	 */
	public void envoiJeuATous() {
		for (Connection connection : this.lesJoueurs.keySet()) {
			this.controle.evenementJeuServeur(MODIFPANELJEU, connection);
		}
	}

	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for (int k = 0; k < NBMURS; k++) {
			this.lesMurs.add(new Mur());
			this.controle.evenementJeuServeur(AJOUTMUR, lesMurs.get(lesMurs.size() - 1).getjLabel());
		}
	}

	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

}
