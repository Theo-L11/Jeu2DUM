package modele;

import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Global, Runnable {

	/**
	 * la collection de murs
	 */
	private Collection lesMurs;
	/**
	 * Joueur qui attaque
	 */
	private Joueur attaquant;
	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur;

	/**
	 * Constructeur
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		super.jLabel = new JLabel();
		super.jLabel.setVisible(false);
		URL resource = getClass().getClassLoader().getResource(BOULE);
		super.jLabel.setIcon(new ImageIcon(resource));
		super.jLabel.setBounds(0, 0, LARGEURBOULE, HAUTEURBOULE);
	}

	/**
	 * Tire d'une boule
	 */
	public void tireBoule(Joueur attaquant, Collection lesMurs) {
		this.attaquant = attaquant;
		this.lesMurs = lesMurs;
		// positionnement de la boule
		if (attaquant.getOrientation() == GAUCHE) {
			posX = attaquant.getPosX() - LARGEURBOULE - 1;
		} else {
			posX = attaquant.getPosX() + LARGEURPERSO + 1;
		}
		posY = attaquant.getPosY() + HAUTEURPERSO / 2;
		// creation d'un nouveau thread pour que le jeu continu meme en tirant une boule
		new Thread(this).start();
	}

	@Override
	public void run() {
		// envoi du son FIGHT
		this.jeuServeur.envoi(FIGHT);
		// afficher l'attaquant à l'étape repos de la marche
		this.attaquant.affiche(MARCHE, 1);
		// rendre boule visite
		this.jLabel.setVisible(true);
		// declaration de la variable victime
		Joueur victime = null;
		// le pas qui permet de faire avancer la boule
		int lePas;
		if (this.attaquant.getOrientation() == GAUCHE) {
			lePas = -NBPAS;
		} else {
			lePas = NBPAS;
		}
		// boucle pour gerer la trajectoire de la boule
		do {
			posX += lePas;
			// la boule avance
			this.jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
			// envoi a tous de la nouvelle zone de jeu
			this.jeuServeur.envoiJeuATous();
			// recuperation de la collection de joueur dans jeuServeur
			Collection lesJoueurs = this.jeuServeur.getLesJoueurs();
			// appel de la methode toucheCollectionObjet pour savoir si victime touchée?
			victime = (Joueur) super.toucheCollectionObjets(lesJoueurs);
		} while (posX >= 0 && posX <= LARGEURARENE && victime == null && this.toucheCollectionObjets(lesMurs) == null);
		// test pour savoir si il y a victime touchée
		if (victime != null && !victime.estMort()) {
			// envoi du son HURT
			this.jeuServeur.envoi(HURT);
			victime.perteVie();
			this.attaquant.gainVie();
			// boucle pour l'affichage de lanimation du blessé
			for (int k = 1; k <= NBETAPESTOUCHE; k++) {
				victime.affiche(TOUCHE, k);
				pause(80, 0);
			}
			if (victime.estMort()) {
				// envoi du son DEATH
				this.jeuServeur.envoi(DEATH);
				// boucle pour l'animation de la Mort
				for (int k = 1; k <= NBETAPESMORT; k++) {
					victime.affiche(MORT, k);
					pause(80, 0);
				}
			} else {
				// si victime pas morte
				victime.affiche(MARCHE, 1);
			}
		}
		// rendre la boule invisible
		this.jLabel.setVisible(false);
		// envoi a tous
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * fais une pause (bloque le processus) d'une durée précise
	 * 
	 * @param millis millisecondes
	 * @param nanos  nanosecondes
	 */
	private void pause(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.out.println("erreur pause");
		}
	}

}
