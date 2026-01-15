package JEST.view;

import JEST.model.cards.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

/**
 * Panel for displaying trophy cards and the reference card.
 * <p>
 * This panel shows the reference card that helps players understand card values,
 * and displays one or two trophy cards depending on the number of players.
 * </p>
 *
 * @author JEST Project
 * @version 1.0
>>>>>>> branch 'master' of https://github.com/tuturd/LO02-JEST
 */
public class TrophyInitializationPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private MainWindow main;
    private JButton referenceCardOriginal, trophy1, trophy2;
    private JLabel referenceLabel, trophiesLabel;

    /**
     * Constructor for the trophy initialization panel.
     * <p>
     * Initializes the reference card button and trophy card buttons.
     * Trophy buttons are hidden by default until trophies are displayed.
     * </p>
     *
     * @param main the main window of the application
     */
    public TrophyInitializationPanel(MainWindow main) {
        this.main = main;

        setLayout(null);

        referenceLabel = new JLabel("Référence", SwingConstants.CENTER);
        referenceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        referenceLabel.setBounds(10, -5, 127, 30);
        add(referenceLabel);

        referenceCardOriginal = new JButton("");
        referenceCardOriginal.setIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/JEST/static/reference_card.png"))));
        referenceCardOriginal.setBounds(10, 25, 127, 181);
        add(this.referenceCardOriginal);

        trophiesLabel = new JLabel("Trophées", SwingConstants.CENTER);
        trophiesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        trophiesLabel.setBounds(147, -5, 264, 30);
        add(trophiesLabel);

        trophy1 = new JButton("");
        trophy1.setBounds(147, 25, 127, 181);
        trophy1.setVisible(false);
        add(trophy1);

        trophy2 = new JButton("");
        trophy2.setBounds(284, 25, 127, 181);
        trophy2.setVisible(false);
        add(trophy2);
    }

    /**
     * Displays trophy cards dynamically.
     * <p>
     * Shows one or two trophy cards depending on the number of players in the game.
     * For 3 players, two trophies are displayed; for 4 players, one trophy is displayed.
     * </p>
     *
     * @param trophyCards the list of trophy cards to display (1 or 2 cards)
     */
    public void displayTrophies(List<Card> trophyCards) {
        if (trophyCards == null || trophyCards.isEmpty()) {
            return;
        }

        trophy1.setIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(trophyCards.get(0).getPicturePath()))));
        trophy1.setVisible(true);

        if (trophyCards.size() > 1) {
            trophy2.setIcon(
                    new ImageIcon(Objects.requireNonNull(getClass().getResource(trophyCards.get(1).getPicturePath()))));
            trophy2.setVisible(true);
        } else {
            trophy2.setVisible(false);
        }
    }

    /**
     * Adds an action listener to the reference card button.
     *
     * @param listener the action listener to add
     */
    public void addReferenceCardListener(ActionListener listener) {
        referenceCardOriginal.addActionListener(listener);
    }
}
