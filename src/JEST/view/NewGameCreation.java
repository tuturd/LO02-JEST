package JEST.view;

import javax.swing.*;

import JEST.model.Game;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class NewGameCreation extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblNombreDeJoueurs, nomJoueur;
	private JButton btn3Players, btn4Players, btnNon, btnOui;
	private JTextField txtPrenomNom;
	private JButton btnSuivant;
	

	public NewGameCreation(MainWindow main) {
		setLayout(null);
		
		this.lblNombreDeJoueurs = new JLabel("Nombre de joueurs ?");
		this.lblNombreDeJoueurs.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblNombreDeJoueurs.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
		this.lblNombreDeJoueurs.setBackground(new Color(255, 255, 128));
		this.lblNombreDeJoueurs.setBounds(167, 30, 322, 50);
		add(this.lblNombreDeJoueurs);
	
		this.btn3Players = new JButton("3");
		this.btn3Players.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		this.btn3Players.setBounds(140, 116, 128, 31);
		this.btn3Players.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNombreDeJoueurs.setVisible(false);
				btn3Players.setVisible(false);
				btn4Players.setVisible(false);
				
				setPlayers(3);
			}
		});
		add(this.btn3Players);
		
		this.nomJoueur = new JLabel("");
		this.nomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
		this.nomJoueur.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
		this.nomJoueur.setBackground(new Color(255, 255, 128));
		this.nomJoueur.setBounds(167, 30, 322, 50);
		this.nomJoueur.setVisible(false);
		add(this.nomJoueur);
		
		this.btn4Players = new JButton("4");
		this.btn4Players.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		this.btn4Players.setBounds(413, 116, 128, 31);
		this.btn4Players.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.destroyInstance();
				Game.getInstance();
				main.show(1);
			}
		});
		add(this.btn4Players);
		
		btnNon = new JButton("NON");
		btnNon.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnNon.setBounds(413, 116, 128, 31);
		btnNon.setVisible(false);
		add(btnNon);
		
		btnOui = new JButton("OUI");
		btnOui.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		btnOui.setBounds(140, 116, 128, 31);
		btnOui.setVisible(false);
		add(btnOui);
		
		txtPrenomNom = new JTextField();
		txtPrenomNom.setBounds(195, 90, 265, 31);
		add(txtPrenomNom);
		txtPrenomNom.setVisible(false);
		txtPrenomNom.setColumns(10);
		
		btnSuivant = new JButton("Suivant");
		btnSuivant.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		btnSuivant.setBounds(413, 157, 128, 31);
		add(btnSuivant);
		btnSuivant.setVisible(false);
	}
	
	private void setPlayers(int playerNumber) {
		this.nomJoueur.setVisible(true);
		this.btnNon.setVisible(true);
		this.btnOui.setVisible(true);
		this.nomJoueur.setText("Le joueur 1 est-il un joueur virtuel ?");
		
		this.btnOui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNon.setVisible(false);
				btnOui.setVisible(false);
				txtPrenomNom.setVisible(true);
				btnSuivant.setVisible(true);
				nomJoueur.setText("Prénom du joueur 1 :");
				
			}
		});
		
		this.btnNon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
