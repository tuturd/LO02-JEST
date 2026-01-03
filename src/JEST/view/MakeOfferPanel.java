package JEST.view;

import JEST.controller.GUIController;
import JEST.model.Player;
import JEST.model.cards.Card;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MakeOfferPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private MainWindow main;
    private GUIController gameController;

    private JLabel lblPlayerInfo;
    private JLabel lblJestInfo;
    private JLabel lblInstruction;

    private JButton btnCard1;
    private JButton btnCard2;

    private Card card1;
    private Card card2;
    private Player player;

    public MakeOfferPanel(MainWindow main) {
        this.main = main;

        setLayout(null);

        // Player info label
        lblPlayerInfo = new JLabel();
        lblPlayerInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblPlayerInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerInfo.setBounds(50, 20, 700, 30);
        add(lblPlayerInfo);

        // Jest info label
        lblJestInfo = new JLabel();
        lblJestInfo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblJestInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblJestInfo.setBounds(50, 55, 700, 25);
        add(lblJestInfo);

        // Instruction label
        lblInstruction = new JLabel("Quelle carte souhaitez-vous laisser visible ?");
        lblInstruction.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblInstruction.setForeground(new Color(0, 100, 0));
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction.setBounds(50, 100, 700, 30);
        add(lblInstruction);

        // Card 1 button
        btnCard1 = new JButton();
        btnCard1.setBounds(150, 160, 150, 215);
        btnCard1.addActionListener(e -> selectCard(1));
        add(btnCard1);

        // Card 2 button
        btnCard2 = new JButton();
        btnCard2.setBounds(500, 160, 150, 215);
        btnCard2.addActionListener(e -> selectCard(2));
        add(btnCard2);
    }

    /**
     * Set up the panel with player and cards information.
     */
    public void setupOffer(Player player, String jestContent, Card card1, Card card2) {
        this.player = player;
        this.card1 = card1;
        this.card2 = card2;

        setBackground(new Color(34, 139, 34));
        btnCard1.setEnabled(true);
        btnCard2.setEnabled(true);

        lblPlayerInfo.setText(player.toString() + " - C'est votre tour de faire une offre");
        lblJestInfo.setText("Votre Jest : " + jestContent);
        lblInstruction.setText("Quelle carte souhaitez-vous laisser visible ?");

        try {
            ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource(card1.getPicturePath())
            ));
            btnCard1.setIcon(icon1);
            btnCard1.setText("");
        } catch (Exception e) {
            btnCard1.setText("Carte 1: " + card1.toString());
        }

        try {
            ImageIcon icon2 = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource(card2.getPicturePath())
            ));
            btnCard2.setIcon(icon2);
            btnCard2.setText("");
        } catch (Exception e) {
            btnCard2.setText("Carte 2: " + card2.toString());
        }
    }

    /**
     * Handle card selection.
     */
    private void selectCard(int cardNumber) {
        if (this.gameController != null) {
            this.gameController.completeIntResponse(cardNumber);
            clear();
        }
    }

    /**
     * Clear the panel after selection.
     */
    public void clear() {
        lblPlayerInfo.setText("");
        lblJestInfo.setText("");
        lblInstruction.setText("");
        btnCard1.setIcon(null);
        btnCard1.setText("");
        btnCard1.setEnabled(false);
        btnCard2.setIcon(null);
        btnCard2.setText("");
        btnCard2.setEnabled(false);
        main.show(3);
    }

    public void setGameController(GUIController gameController) {
        this.gameController = gameController;
    }
}
