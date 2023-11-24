package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EntreeJeu extends JFrame implements Global {

	private JPanel frmUrbanMarginal;
	private JTextField txtIPServer;
	private Controle controle;

	/**
	 * click sur le bouton exit ferme l'application
	 */
	private void btnExit_Click() {
		System.exit(0);
	}

	/**
	 * click sur le bouton start pour lancer le serveur
	 */
	private void btnStart_Click() {
		this.controle.evenementEntreeJeu("serveur");
	}

	/**
	 * click sur le bouton connect pour se connecter a un serveur
	 */
	private void btnConnect_Click() {
		this.controle.evenementEntreeJeu(this.txtIPServer.getText());
	}

	/**
	 * Create the frame.
	 */
	public EntreeJeu(Controle controle) {
		setTitle("Urban Marginal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 165);
		frmUrbanMarginal = new JPanel();
		frmUrbanMarginal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(frmUrbanMarginal);
		frmUrbanMarginal.setLayout(null);

		txtIPServer = new JTextField();
		txtIPServer.setText("127.0.0.1");
		txtIPServer.setBounds(73, 58, 98, 20);
		frmUrbanMarginal.add(txtIPServer);
		txtIPServer.setColumns(10);

		JLabel lblStartServer = new JLabel("Start a server :");
		lblStartServer.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblStartServer.setLabelFor(lblStartServer);
		lblStartServer.setBounds(10, 11, 98, 14);
		frmUrbanMarginal.add(lblStartServer);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart_Click();
			}
		});
		btnStart.setBounds(188, 8, 89, 23);
		frmUrbanMarginal.add(btnStart);

		JLabel lblConnectServer = new JLabel("Connect an existing server :");
		lblConnectServer.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblConnectServer.setBounds(10, 36, 171, 20);
		frmUrbanMarginal.add(lblConnectServer);

		JLabel lblIPServer = new JLabel("IP server :");
		lblIPServer.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblIPServer.setBounds(10, 60, 75, 14);
		frmUrbanMarginal.add(lblIPServer);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect_Click();
			}
		});
		btnConnect.setBounds(188, 57, 89, 23);
		frmUrbanMarginal.add(btnConnect);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_Click();
			}
		});
		btnExit.setBounds(188, 92, 89, 23);
		frmUrbanMarginal.add(btnExit);
		
		this.controle = controle;
	}
}
