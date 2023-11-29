package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global {

	/**
	 * pseudo saisi
	 */
	private String pseudo;

	/**
	 * vie restante du joueur
	 */
	private int vie;

	/**
	 * tourner vers la gauche (0) ou vers la droite (1)
	 */
	private int orientation;

	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso;
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur;
	/**
	 * numéro d'étape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape;
	/**
	 * la boule du joueur
	 */
	private Boule boule;
	/**
	 * enregistre le message
	 */
	private JLabel message;

	/**
	 * @return le pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * @return l'orientation du personnage
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Constructeur
	 * 
	 * @param jeuServeur le jeuServeur dans lequel se trouve le joueur
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE;
		this.etape = 1;
		this.orientation = DROITE;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position,
	 * affichage, création de la boule)
	 * 
	 * @param pseudo     pseudo du joueur
	 * @param numPerso   numero du perso choisi
	 * @param lesJoueurs les joueurs dans l'arene
	 * @param lesMurs    les murs dans l'arene
	 */
	public void initPerso(String pseudo, int numPerso, Collection lesJoueurs, Collection lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur " + pseudo + " - num perso " + numPerso + " créé");
		// creation du JLabel provenant de la classe mere
		super.jLabel = new JLabel();
		// creation de la propriete message pour enregistrer le message sous le perso
		this.message = new JLabel();
		// creation de la boule a tiré
		this.boule = new Boule(this.jeuServeur);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		// appel de la methode premiere position
		this.premierePosition(lesJoueurs, lesMurs);
		// trois appels de la methode de JeuServeur pour joueur et son message et la boule
		this.jeuServeur.ajoutJLabelJeuArene(jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(message);
		this.jeuServeur.ajoutJLabelJeuArene(boule.getjLabel());
		this.affiche(MARCHE, this.etape);

	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre
	 * joueur ou un mur)
	 * 
	 * @param lesJoueurs les joueurs dans l'arene
	 * @param lesMurs    les murs dans l'arene
	 */
	private void premierePosition(Collection lesJoueurs, Collection lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO));
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE));
		} while (toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null);
	}

	/**
	 * affiche le personnage et son message
	 * 
	 * @param etat  etat actuelle du personnage
	 * @param etape etape dans laquelle se trouve le personnage
	 */
	public void affiche(String etat, int etape) {
		// positionnement du personnage et affectation de la bonne image
		super.jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		String chemin = CHEMINPERSONNAGES + PERSO + this.numPerso + etat + etape + "d" + this.orientation
				+ EXTFICHIERPERSO;
		URL resource = getClass().getClassLoader().getResource(chemin);
		super.jLabel.setIcon(new ImageIcon(resource));
		// positionnement et remplissage du message sous le perosnnage
		this.message.setBounds(posX - 10, posY + HAUTEURPERSO, LARGEURPERSO + 10, HAUTEURMESSAGE);
		this.message.setText(pseudo + " : " + vie);
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action(Integer action, Collection lesJoueurs, Collection lesMurs) {
		switch(action) {
		case KeyEvent.VK_LEFT:
			orientation = GAUCHE;
			posX = deplace(posX, action, -NBPAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_RIGHT:
			orientation = DROITE;
			posX = deplace(posX, action, NBPAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_UP:
			posY = deplace(posY, action, -NBPAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_DOWN:
			posY = deplace(posY, action, NBPAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_SPACE:
			if(!this.boule.getjLabel().isVisible()) {
				this.boule.tireBoule(this, lesMurs);
			}
		}
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Gère le déplacement du personnage 
	 * @param position position de départ
	 * @param action gauche, droite, haut ou bas
	 * @param lepas valeur de déplacement (positif ou négatif)
	 * @param max valeur à ne pas dépasser
	 * @param lesJoueurs collection de joueurs pour éviter les collisions
	 * @param lesMurs collection de murs pour éviter les collisions
	 * @return nouvelle position
	 */
	private int deplace(int position, // position de départ
			int action, // gauche, droite, haut, bas
			int lepas, // valeur du déplacement (positif ou négatif)
			int max, // valeur à ne pas dépasser
			Collection lesJoueurs, // les autres joueurs (pour éviter les collisions)
			Collection lesMurs) { // les murs (pour éviter les collisions)
		int ancpos = position ;
		position += lepas ;
		position = Math.max(position, 0) ;
		position = Math.min(position,  max) ;
		if (action==KeyEvent.VK_LEFT || action==KeyEvent.VK_RIGHT) {
			posX = position ;
		}else{
			posY = position ;
		}
		// controle s'il y a collision, dans ce cas, le personnage reste sur place
		if (toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null) {
			position = ancpos ;
		}
		// passe à l'étape suivante de l'animation de la marche
		etape = (etape % NBETAPESMARCHE) + 1 ;
		return position ;
	}


	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		this.vie += GAIN;
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		this.vie = Math.max(0, this.vie - PERTE);
	}

	/**
	 * vrai si la vie est à 0
	 * 
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return (this.vie == 0);
	}

	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}

}
