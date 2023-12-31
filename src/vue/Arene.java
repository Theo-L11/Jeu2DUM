package vue;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * frame de l'ar�ne du jeu
 * 
 * @author emds
 *
 */
public class Arene extends JFrame implements Global {

	/**
	 * Panel g�n�ral
	 */
	private JPanel contentPane;
	/**
	 * Panel contenant les murs
	 */
	private JPanel jpnMurs;
	/**
	 * Panel pour les joueurs
	 */
	private JPanel jpnJeu;
	/**
	 * Zone de saisie du t'chat
	 */
	private JTextField txtSaisie;
	/**
	 * Zone d'affichage du t'chat
	 */
	private JTextArea txtChat;
	/**
	 * controleur propertie
	 */
	private Controle controle;
	/**
	 * permet de savoir si c'est une arene client ou serveur
	 */
	private boolean client;
	/**
	 * Talbeau des sons de l'ar�ne
	 */
	private Son[] lesSons = new Son[SON.length];

	/**
	 * @return the txtChat
	 */
	public String getTxtChat() {
		return txtChat.getText();
	}

	/**
	 * @param txtChat the txtChat to set
	 */
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}

	/**
	 * @return the jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}

	/**
	 * @return the jpnJeu
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}

	/**
	 * @param jpnMurs the jpnMurs to set
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}

	/**
	 * 
	 * @param jpnJeu le jpnJeu a modifi�
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
		this.contentPane.requestFocus();
	}

	/**
	 * ajoute une phrase dans la zone de tchat
	 * 
	 * @param phrase la phrase a ajouter
	 */
	public void ajoutTChat(String phrase) {
		this.txtChat.setText(txtChat.getText() + phrase + "\r\n");
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}

	/**
	 * Ajoute un mur dans le panel des murs
	 * 
	 * @param unMur le mur � ajouter
	 */
	public void ajoutMurs(Object unMur) {
		jpnMurs.add((JLabel) unMur);
		jpnMurs.repaint();
	}

	/**
	 * Ajoute pour le serveur un nouveau JLabel dans le panel jpnJeu
	 * 
	 * @param info info pour l'ajout
	 */
	public void ajoutLabelJeu(JLabel info) {
		jpnJeu.add(info);
		jpnJeu.repaint();
	}
	
	/**
	 * Joue le son correspondant au num�ro re�u
	 * @param numSon num�ro du son (0 : fight, 1 : hurt; 2 : death)
	 */
	public void joueSon(Integer numSon) {
		this.lesSons[numSon].play();
	}

	/**
	 * Ev�n�ment touche press�e dans la zone de saisie
	 * 
	 * @param e informations sur la touche
	 */
	public void txtSaisie_KeyPressed(KeyEvent e) {
		// si validation
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// si la zone de saisie n'est pas vide
			if (!this.txtSaisie.getText().equals("")) {
				this.controle.evenementArene(this.txtSaisie.getText());
				this.txtSaisie.setText("");
				this.contentPane.requestFocus();
			}
		}
	}
	/**
	 * �v�nement touche pr�ss�e dans le contentPane
	 * @param e infos sur la touche
	 */
	public void contentPane_keyPressed(KeyEvent e) {
		// initialisation de la variable de touche
		int touche = -1;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_SPACE:
			touche = e.getKeyCode();
			break;
		}
		// si touche ne contient pas -1 alors on envoie l'info au controleur
		if(touche != -1) {
			this.controle.evenementArene(touche);
		}
	}

	/**
	 * Create the frame.
	 */
	public Arene(Controle controle, String typeJeu) {
		this.client = typeJeu.equals(CLIENT);
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
		this.pack();
		// interdiction de changer la taille
		this.setResizable(false);

		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		if (this.client) {
			contentPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_keyPressed(e);
				}
			});
		}
		setContentPane(contentPane);
		contentPane.setLayout(null);

		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);
		contentPane.add(jpnJeu);

		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);
		contentPane.add(jpnMurs);

		if (this.client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					txtSaisie_KeyPressed(e);
				}
			});
			txtSaisie.setBounds(0, 600, 800, 25);
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10);
		}

		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, 800, 140);
		contentPane.add(jspChat);

		txtChat = new JTextArea();
		txtChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				contentPane_keyPressed(e);
			}
		});
		jspChat.setViewportView(txtChat);
		txtChat.setEditable(false);

		JLabel lblFond = new JLabel("");
		URL resource = getClass().getClassLoader().getResource(FONDARENE);
		lblFond.setIcon(new ImageIcon(resource));
		lblFond.setBounds(0, 0, 800, 600);
		contentPane.add(lblFond);
		
		// gestion des sons pour le client
				if (client) {
					for (int k=0 ; k<SON.length ; k++) {
						lesSons[k] = new Son(getClass().getClassLoader().getResource(SON[k])) ;
					}
				}

		this.controle = controle;

	}

}