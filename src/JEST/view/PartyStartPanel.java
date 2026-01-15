package JEST.view;

import JEST.controller.GUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the panel where we have the choice if we want to start a new game or load one.
 */
public class PartyStartPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private GUIController gameController;

    /**
     * Create the panel in the main window.
     * @param main the main window.
     */
    public PartyStartPanel(MainWindow main) {
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
                if (gameController != null) {
                    gameController.completeIntResponse(1);
                }
                main.show(1);
            }
        });
        add(btnNouvellePartie);

        JButton btnCharger = new JButton("Charger...");
        btnCharger.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        btnCharger.setBounds(455, 101, 173, 31);
        btnCharger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameController != null) {
                    gameController.completeIntResponse(2);
                }
            }
        });
        add(btnCharger);
    }

    public void setGameController(GUIController gameController) {
        this.gameController = gameController;
    }
}

