package JEST.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PartyStartInterface extends JFrame {
	public PartyStartInterface() {
		initialize();
	}

    private JButton referenceCardOriginal;

    private void initialize() {
        setBounds(100, 100, 792, 593);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        referenceCardOriginal = new JButton("");
        referenceCardOriginal.setIcon(
                new ImageIcon(
                        Objects.requireNonNull(
                        		getClass().getResource("/JEST/static/reference_card.png")
                        )
                )
        );
        referenceCardOriginal.setBounds(25, 32, 127, 181);
        getContentPane().add(referenceCardOriginal);
        referenceCardOriginal.setVisible(false);
        
        JLabel textChoiceBeginning = new JLabel("<html><div style='text-align:center;'>"
        		  + "Voulez-vous commencer une nouvelle partie<br>"
        		  + "ou charger une partie existante ?"
        		  + "</div></html>");
        textChoiceBeginning.setHorizontalAlignment(SwingConstants.CENTER);
        textChoiceBeginning.setBackground(new Color(255, 255, 128));
        textChoiceBeginning.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        textChoiceBeginning.setBounds(119, 32, 509, 50);
        getContentPane().add(textChoiceBeginning);
        
        JButton btnNouvellePartie = new JButton("Nouvelle partie");
        btnNouvellePartie.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        btnNouvellePartie.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNouvellePartie.setBounds(129, 101, 173, 31);
        getContentPane().add(btnNouvellePartie);
        
        JButton btnCharger = new JButton("Charger...");
        btnCharger.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        btnCharger.setBounds(455, 101, 173, 31);
        getContentPane().add(btnCharger);
    }

    public void addReferenceCardListener(ActionListener listener) {
        referenceCardOriginal.addActionListener(listener);
    }
}
