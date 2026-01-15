package JEST.view;

import JEST.controller.GUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Initial panel displayed when starting the application.
 * <p>
 * This panel allows the user to choose between starting a new game
 * or loading an existing saved game.
 * </p>
 *
 * @author JEST Project
 * @version 1.0
 */
public class PartyStartPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private GUIController gameController;

    /**
<<<<<<< HEAD
     * Create the panel in the main window.
     * @param main the main window.
=======
     * Constructor for the party start panel.
     * <p>
     * Initializes UI components including buttons for starting a new game
     * or loading an existing game.
     * </p>
     *
     * @param main the main window of the application
>>>>>>> branch 'master' of https://github.com/tuturd/LO02-JEST
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

    /**
     * Sets the game controller for this panel.
     *
     * @param gameController the GUI game controller
     */
    public void setGameController(GUIController gameController) {
        this.gameController = gameController;
    }
}

