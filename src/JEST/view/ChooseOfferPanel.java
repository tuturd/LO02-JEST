package JEST.view;

import JEST.controller.GUIController;
import JEST.model.Player;
import JEST.model.cards.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChooseOfferPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private MainWindow main;
    private GUIController gameController;

    private JLabel lblPlayerInfo;
    private JLabel lblJestInfo;
    private JLabel lblInstruction;
    private JPanel offersPanel;

    private Player currentPlayer;
    private List<Player> availablePlayers;

    public ChooseOfferPanel(MainWindow main) {
        this.main = main;

        setLayout(null);
        setBackground(new Color(34, 139, 34));

        // Player info label
        lblPlayerInfo = new JLabel();
        lblPlayerInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lblPlayerInfo.setForeground(Color.WHITE);
        lblPlayerInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlayerInfo.setBounds(50, 20, 700, 30);
        add(lblPlayerInfo);

        // Jest info label
        lblJestInfo = new JLabel();
        lblJestInfo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblJestInfo.setForeground(Color.YELLOW);
        lblJestInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblJestInfo.setBounds(50, 55, 700, 25);
        add(lblJestInfo);

        // Instruction label
        lblInstruction = new JLabel("Choisissez une carte parmi les offres disponibles");
        lblInstruction.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblInstruction.setForeground(new Color(255, 255, 200));
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstruction.setBounds(50, 90, 700, 30);
        add(lblInstruction);

        // Panel for offers
        offersPanel = new JPanel();
        offersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        offersPanel.setBackground(new Color(34, 139, 34));
        offersPanel.setBounds(20, 130, 760, 450);
        add(offersPanel);
    }

    /**
     * Set up the panel with player and available offers.
     */
    public void setupChooseOffer(Player player, String jestContent, List<Player> availablePlayers) {
        this.currentPlayer = player;
        this.availablePlayers = availablePlayers;

        setBackground(new Color(34, 139, 34));

        lblPlayerInfo.setText(player.toString() + " - Choisissez une carte");
        lblJestInfo.setText("Votre Jest : " + jestContent);
        lblInstruction.setText("Choisissez une carte parmi les offres disponibles");

        offersPanel.removeAll();

        for (int i = 0; i < availablePlayers.size(); i++) {
            Player offerPlayer = availablePlayers.get(i);

            Card visibleCard = offerPlayer.getCurrentOffer().getCard(true);
            JPanel cardPanel = createCardPanel(offerPlayer, visibleCard, true, i, 1);
            offersPanel.add(cardPanel);

            JPanel hiddenPanel = createHiddenCardPanel(offerPlayer, i, 2);
            offersPanel.add(hiddenPanel);
        }

        offersPanel.revalidate();
        offersPanel.repaint();
    }

    /**
     * Create a panel for a visible card with player name.
     */
    private JPanel createCardPanel(Player offerPlayer, Card card, boolean isVisible, int playerIndex, int cardChoice) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(new Color(50, 150, 50));
        panel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        panel.setPreferredSize(new Dimension(140, 220));

        // Player name label
        JLabel nameLabel = new JLabel("<html><center>" + offerPlayer.toString() + "<br>(Visible)</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, BorderLayout.NORTH);

        // Card button
        JButton btnCard = new JButton();
        btnCard.setPreferredSize(new Dimension(120, 170));

        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(
                    getClass().getResource(card.getPicturePath())
            ).getImage().getScaledInstance(120, 170, Image.SCALE_SMOOTH));
            btnCard.setIcon(icon);
            btnCard.setText("");
        } catch (Exception e) {
            btnCard.setText("<html><center>" + card.toString() + "</center></html>");
        }

        btnCard.addActionListener(e -> selectOffer(playerIndex, cardChoice));
        panel.add(btnCard, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create a panel for the hidden card with player name.
     */
    private JPanel createHiddenCardPanel(Player offerPlayer, int playerIndex, int cardChoice) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(new Color(50, 150, 50));
        panel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        panel.setPreferredSize(new Dimension(140, 220));

        // Player name label
        JLabel nameLabel = new JLabel("<html><center>" + offerPlayer.toString() + "<br>(Cachée)</center></html>", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel, BorderLayout.NORTH);

        // Card button
        JButton btnCard = new JButton();
        btnCard.setPreferredSize(new Dimension(120, 170));

        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(
                    getClass().getResource("/JEST/static/back.png")
            ).getImage().getScaledInstance(120, 170, Image.SCALE_SMOOTH));
            btnCard.setIcon(icon);
            btnCard.setText("");
        } catch (Exception e) {
            btnCard.setText("<html><center>Carte<br>Cachée</center></html>");
        }

        btnCard.addActionListener(e -> selectOffer(playerIndex, cardChoice));
        panel.add(btnCard, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Handle offer selection.
     */
    private void selectOffer(int playerIndex, int cardChoice) {
        if (gameController != null) {
            gameController.completeIntArrayResponse(new int[]{playerIndex, cardChoice});
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
        offersPanel.removeAll();
        offersPanel.revalidate();
        offersPanel.repaint();
        main.show(3);
    }

    public void setGameController(GUIController gameController) {
        this.gameController = gameController;
    }
}
