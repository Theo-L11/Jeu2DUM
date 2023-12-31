package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Cursor;
import java.awt.Dimension;

/**
 * Frame du choix du joueur
 * 
 * @author emds
 *
 */
public class ChoixJoueur extends JFrame implements Global {

	/**
	 * Panel g�n�ral
	 */
	private JPanel contentPane;
	/**
	 * Zone de saisie du pseudo
	 */
	private JTextField txtPseudo;

	/**
	 * label qui permet d'afficher le personnage
	 */
	private JLabel lblPersonnage;

	/**
	 * fais le lien avec le controleur
	 */
	private Controle controle;

	/**
	 * numero du personnage affich�
	 */
	private int numPerso;
	
	/**
	 * son de bienvenue
	 */
	private Son sonBienvenue;
	/**
	 * son sur clic fleche gauche
	 */
	private Son sonFlecheGauche;
	/**
	 * son sur clic fleche droite
	 */
	private Son sonFlecheDroite;
	/**
	 * son sur clic go
	 */
	private Son sonGo;

	/**
	 * Clic sur la fl�che "pr�c�dent" pour afficher le personnage pr�c�dent
	 */
	private void lblPrecedent_clic() {
		// System.out.println("Clic sur precedent");
		numPerso = ((numPerso + 1) % NBPERSOS) + 1;
		affichePerso();
		sonFlecheGauche.play();
	}

	/**
	 * Clic sur la fl�che "suivant" pour afficher le personnage suivant
	 */
	private void lblSuivant_clic() {
		// System.out.println("Clic sur suivant");
		numPerso = (numPerso % NBPERSOS) + 1;
		affichePerso();
		sonFlecheGauche.play();
	}

	/**
	 * Clic sur GO pour envoyer les informations
	 */
	private void lblGo_clic() {
		if (this.txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			this.txtPseudo.requestFocus();
		} else {
			this.controle.evenementChoixJoueur(this.txtPseudo.getText(), this.numPerso);
		}
		sonGo.play();
	}

	/**
	 * affiche le personnage en fonction de son numero
	 */
	private void affichePerso() {
		String chemin = CHEMINPERSONNAGES + PERSO + this.numPerso + MARCHE + 1 + "d" + 1 + EXTFICHIERPERSO;
		URL resource = getClass().getClassLoader().getResource(chemin);
		this.lblPersonnage.setIcon(new ImageIcon(resource));
	}

	/**
	 * change la souris en souris normale lorsqu'elle ne pointe aucune des "zones"
	 */
	private void sourisNormale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * change la souris en doigt lorsqu'elle est point� sur une fleche ou sur le bouton GO
	 */
	private void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
		// interdiction de changer la taille
		this.setResizable(false);

		setTitle("Choice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblPersonnage = new JLabel("");
		lblPersonnage.setBounds(142, 111, 120, 123);
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPersonnage);

		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblPrecedent_clic();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});

		JLabel lblSuivant = new JLabel("");
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});

		JLabel lblGo = new JLabel("");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});

		txtPseudo = new JTextField();
		txtPseudo.setBounds(142, 245, 120, 20);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);

		lblGo.setBounds(311, 202, 65, 61);
		contentPane.add(lblGo);
		lblSuivant.setBounds(301, 145, 25, 46);
		contentPane.add(lblSuivant);
		lblPrecedent.setBounds(65, 146, 31, 45);
		contentPane.add(lblPrecedent);

		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = FONDCHOIX;
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);

		// positionnement sur la zone de saisie
		txtPseudo.requestFocus();

		this.controle = controle;

		this.numPerso = 1;
		this.affichePerso();
		
		sonBienvenue = new Son(getClass().getClassLoader().getResource(SONWELCOME));
		sonFlecheGauche = new Son(getClass().getClassLoader().getResource(SONPRECEDENT));
		sonFlecheDroite = new Son(getClass().getClassLoader().getResource(SONSUIVANT));
		sonGo = new Son(getClass().getClassLoader().getResource(SONGO));
		sonBienvenue.play();

	}
}