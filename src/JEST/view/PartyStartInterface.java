package JEST.view;

import javax.swing.*;

import JEST.model.Game;

import java.awt.event.ActionListener;
import java.util.Objects;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PartyStartInterface extends JPanel {

	private static final long serialVersionUID = 1L;

	public PartyStartInterface(MainWindow main) {
		setLayout(null);

		JLabel textChoiceBeginning = new JLabel(
				"<html><div style='text-align:center;'>" + "Voulez-vous commencer une nouvelle partie<br>"
						+ "ou charger une partie existante ?" + "</div></html>");
		textChoiceBeginning.setHorizontalAlignment(SwingConstants.CENTER);
		textChoiceBeginning.setBackground(new Color(255, 255, 255));
		textChoiceBeginning.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		textChoiceBeginning.setBounds(119, 32, 509, 50);
		add(textChoiceBeginning);
		
		JButton btnNouvellePartie = new JButton("Nouvelle partie");
		btnNouvellePartie.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnNouvellePartie.setBounds(129, 101, 173, 31);
		btnNouvellePartie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.destroyInstance();
				Game.getInstance();
				main.show(1);
			}
		});
		add(btnNouvellePartie);
		
		JButton btnCharger = new JButton("Charger...");
		btnCharger.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnCharger.setBounds(455, 101, 173, 31);
		btnCharger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//à compléter
			}
		});
		add(btnCharger);
	}
}

