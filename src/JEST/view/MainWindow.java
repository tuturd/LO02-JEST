package JEST.view;

import JEST.controller.GUIController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * It creates the window where we add the panel adapted to the game' situation.
 */
public class MainWindow {

    private static final String[] STEPS = {
            "START",
            "CONFIG_NEW",
            "LOAD",
            "TROPHY_INITIALIZATION",
            "MAKE_OFFER",
            "CHOOSE_OFFER",
            "RESULT"
    };
    private JFrame frame;
    private JPanel container;
    private CardLayout layout;
    private JTextArea logArea;
    private PartyStartPanel partyStartPanel;
    private NewGameCreationPanel newGameCreationPanel;
    private TrophyInitializationPanel trophyInitializationPanel;
    private MakeOfferPanel makeOfferPanel;
    private ChooseOfferPanel chooseOfferPanel;
    private GUIController gameController;

    /**
     * Create the window and initialize all the panels.
     */
    public MainWindow() {
        this.frame = new JFrame("JEST");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 700);
        this.frame.setLayout(new BorderLayout());

        this.layout = new CardLayout();
        this.container = new JPanel(layout);

        this.gameController = new GUIController(this);

        this.partyStartPanel = new PartyStartPanel(this);
        this.partyStartPanel.setGameController(gameController);

        this.newGameCreationPanel = new NewGameCreationPanel(this);
        this.newGameCreationPanel.setGameController(gameController);

        this.trophyInitializationPanel = new TrophyInitializationPanel(this);

        this.makeOfferPanel = new MakeOfferPanel(this);
        this.makeOfferPanel.setGameController(gameController);

        this.chooseOfferPanel = new ChooseOfferPanel(this);
        this.chooseOfferPanel.setGameController(gameController);

        this.container.add(partyStartPanel, STEPS[0]);
        this.container.add(newGameCreationPanel, STEPS[1]);
        this.container.add(trophyInitializationPanel, STEPS[3]);
        this.container.add(makeOfferPanel, STEPS[4]);
        this.container.add(chooseOfferPanel, STEPS[5]);

        this.logArea = new JTextArea(8, 50);
        this.logArea.setEditable(false);
        this.logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        this.logArea.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(new TitledBorder("Logs du jeu"));

        this.frame.add(container, BorderLayout.CENTER);
        this.frame.add(scrollPane, BorderLayout.SOUTH);

        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }

    public GUIController getGameController() {
        return this.gameController;
    }

    public JEST.model.Game getGame() {
        return JEST.model.Game.getInstance();
    }

    public ChooseOfferPanel getChooseAnOffer() {
        return this.chooseOfferPanel;
    }

    public TrophyInitializationPanel getTrophyInitialization() {
        return this.trophyInitializationPanel;
    }

    public MakeOfferPanel getMakeOffer() {
        return this.makeOfferPanel;
    }

    /**
     * Add a log message to the log area.
     */
    public void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    /**
     * Clear all logs.
     */
    public void clearLogs() {
        SwingUtilities.invokeLater(() -> {
            logArea.setText("");
        });
    }

    public void show(int step) {
        layout.show(container, STEPS[step]);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}