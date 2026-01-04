package JEST.controller;

import JEST.model.Player;
import JEST.model.cards.Card;
import JEST.view.MainWindow;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Graphical User Interface implementation using existing View classes.
 * This implementation bridges the GameInterface with the MainWindow and its panels.
 */
public class GUIController implements GameController {

    private MainWindow mainWindow;
    private CompletableFuture<Integer> pendingIntResponse;
    private CompletableFuture<String> pendingStringResponse;
    private CompletableFuture<Boolean> pendingBooleanResponse;
    private CompletableFuture<int[]> pendingIntArrayResponse;

    /**
     * Constructor for the GUI controller.
     * Initializes the controller with a reference to the main window.
     *
     * @param mainWindow the main window of the application
     */
    public GUIController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Set the main window reference.
     *
     * @param mainWindow the main window of the application
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Complete a pending integer response.
     * This method is called by the view to provide a response to a pending question.
     *
     * @param value the integer value to return
     */
    public void completeIntResponse(int value) {
        if (pendingIntResponse != null) {
            pendingIntResponse.complete(value);
        }
    }

    /**
     * Complete a pending string response.
     * This method is called by the view to provide a response to a pending question.
     *
     * @param value the string value to return
     */
    public void completeStringResponse(String value) {
        if (pendingStringResponse != null) {
            pendingStringResponse.complete(value);
        }
    }

    /**
     * Complete a pending boolean response.
     * This method is called by the view to provide a response to a pending question.
     *
     * @param value the boolean value to return
     */
    public void completeBooleanResponse(boolean value) {
        if (pendingBooleanResponse != null) {
            pendingBooleanResponse.complete(value);
        }
    }

    /**
     * Complete a pending int array response.
     * This method is called by the view to provide a response to a pending question.
     *
     * @param value the int array value to return
     */
    public void completeIntArrayResponse(int[] value) {
        if (pendingIntArrayResponse != null) {
            pendingIntArrayResponse.complete(value);
        }
    }

    /**
     * Ask the user to start a new game or load an existing one using the GUI.
     * Shows the appropriate panel and waits for the user's response.
     *
     * @return 1 for new game, 2 for load game
     */
    @Override
    public int askNewOrLoadGame() {
        pendingIntResponse = new CompletableFuture<>();

        SwingUtilities.invokeLater(() -> {
            mainWindow.show(0);
        });

        try {
            return pendingIntResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * Ask for the number of players using the GUI.
     * Waits for the user's response through the interface.
     *
     * @return the number of players (3 or 4)
     */
    @Override
    public int askNumberOfPlayers() {
        pendingIntResponse = new CompletableFuture<>();

        try {
            return pendingIntResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    /**
     * Ask if a player is virtual using the GUI.
     * Waits for the user's response through the interface.
     *
     * @param playerNumber the player number
     * @return true if virtual, false otherwise
     */
    @Override
    public boolean askIsVirtualPlayer(int playerNumber) {
        pendingBooleanResponse = new CompletableFuture<>();

        try {
            return pendingBooleanResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ask for the first name of a player using the GUI.
     * Waits for the user's response through the interface.
     *
     * @param playerNumber the player number
     * @return the first name
     */
    @Override
    public String askFirstName(int playerNumber) {
        pendingStringResponse = new CompletableFuture<>();

        try {
            return pendingStringResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Ask for the last name of a player using the GUI.
     * Waits for the user's response through the interface.
     *
     * @param playerNumber the player number
     * @return the last name
     */
    @Override
    public String askLastName(int playerNumber) {
        pendingStringResponse = new CompletableFuture<>();

        try {
            return pendingStringResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Ask for the strategy of a virtual player using the GUI.
     * Waits for the user's response through the interface.
     *
     * @param playerNumber the player number
     * @return "1" for random, "2" for defensive, "3" for aggressive
     */
    @Override
    public String askStrategy(int playerNumber) {
        pendingStringResponse = new CompletableFuture<>();

        try {
            return pendingStringResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    /**
     * Display a message about player creation in the GUI log.
     *
     * @param playerNumber the player number
     * @param firstName    the first name
     * @param lastName     the last name
     * @param isVirtual    true if virtual player
     */
    @Override
    public void displayPlayerCreated(int playerNumber, String firstName, String lastName, boolean isVirtual) {
        String message = isVirtual
                ? String.format(">>> Joueur virtuel n°%d créé", playerNumber)
                : String.format(">>> Joueur n°%d créé : %s %s", playerNumber, firstName, lastName);
        mainWindow.addLog(message);
    }

    /**
     * Display the trophy cards in the GUI.
     * Shows the trophy initialization panel with the trophy cards.
     *
     * @param trophyCards the trophy cards to display
     */
    @Override
    public void displayTrophyCards(List<Card> trophyCards) {
        String message = trophyCards.size() == 1
                ? "Le trophée est : " + trophyCards
                : "Les trophées sont : " + trophyCards;
        mainWindow.addLog(message);

        SwingUtilities.invokeLater(() -> {
            mainWindow.getTrophyInitialization().displayTrophies(trophyCards);
            mainWindow.show(3);
        });
    }

    /**
     * Ask a player to make an offer using the GUI.
     * Shows the make offer panel and waits for the player's choice.
     *
     * @param player the player making the offer
     * @param jest   the player's current jest
     * @param c1     first card
     * @param c2     second card
     * @return the index of the card to show face up (1 or 2)
     */
    @Override
    public int askMakeOffer(Player player, String jest, Card c1, Card c2) {
        pendingIntResponse = new CompletableFuture<>();

        SwingUtilities.invokeLater(() -> {
            mainWindow.getMakeOffer().setupOffer(player, jest, c1, c2);
            mainWindow.show(4);
        });

        try {
            return pendingIntResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * Ask a player to choose an offer using the GUI.
     * Shows the choose offer panel and waits for the player's choice.
     *
     * @param player           the player choosing
     * @param jest             the player's current jest
     * @param availablePlayers the list of players with available offers
     * @return an array with [playerIndex, cardChoice] where cardChoice is 1 for face-up, 2 for face-down
     */
    @Override
    public int[] askChooseOffer(Player player, String jest, List<Player> availablePlayers) {
        pendingIntArrayResponse = new CompletableFuture<>();

        SwingUtilities.invokeLater(() -> {
            mainWindow.getChooseAnOffer().setupChooseOffer(player, jest, availablePlayers);
            mainWindow.show(5);
        });

        try {
            return pendingIntArrayResponse.get();
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{0, 1};
        }
    }

    /**
     * Display the card added to a player's jest in the GUI log.
     *
     * @param player the player who received the card
     * @param card   the card that was added
     */
    @Override
    public void displayCardAddedToJest(Player player, Card card) {
        mainWindow.addLog(player + " a ajouté à son Jest : " + card);
    }

    /**
     * Display the final ranking and winner in the GUI.
     * Shows a dialog box with the ranking and logs the result.
     *
     * @param ranking the ranking as a string
     */
    @Override
    public void displayWinner(String ranking) {
        mainWindow.addLog("\n=== FIN DE PARTIE ===");
        mainWindow.addLog(ranking);

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    mainWindow.getFrame(),
                    ranking,
                    "JEST - Fin de partie",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    /**
     * Ask if the user wants to save the game using a GUI dialog.
     * Shows a confirmation dialog.
     *
     * @return true if the user wants to save, false otherwise
     */
    @Override
    public boolean askSaveGame() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        SwingUtilities.invokeLater(() -> {
            int choice = JOptionPane.showConfirmDialog(
                    mainWindow.getFrame(),
                    "Voulez-vous sauvegarder la partie ?",
                    "JEST - Sauvegarde",
                    JOptionPane.YES_NO_OPTION
            );
            future.complete(choice == JOptionPane.YES_OPTION);
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ask for the save file name using a GUI dialog.
     * Shows an input dialog for the file name.
     *
     * @return the file name without extension
     */
    @Override
    public String askSaveFileName() {
        CompletableFuture<String> future = new CompletableFuture<>();

        SwingUtilities.invokeLater(() -> {
            String fileName = JOptionPane.showInputDialog(
                    mainWindow.getFrame(),
                    "Entrez le nom de la sauvegarde (sans extension) :",
                    "JEST - Sauvegarde",
                    JOptionPane.QUESTION_MESSAGE
            );
            future.complete(fileName != null ? fileName : "save");
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return "save";
        }
    }

    /**
     * Display a message in the GUI.
     * Logs the message and shows a dialog for errors and information messages.
     *
     * @param message the message to display
     * @param type    the type of message (NORMAL, INFORMATION, ERROR)
     */
    @Override
    public void displayMessage(String message, MessageType type) {
        mainWindow.addLog(message);
        System.out.println(message);

        if (type == MessageType.INFORMATION || type == MessageType.ERROR) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        mainWindow.getFrame(),
                        message,
                        "JEST",
                        type == MessageType.ERROR ?
                                JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE
                );
            });
        }
    }

    /**
     * Ask the user to select a file to load using a GUI dialog.
     * Shows a combo box with available save files.
     *
     * @param fileNames the list of available save files
     * @return the index of the selected file
     */
    @Override
    public int askSelectFileToLoad(List<String> fileNames) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        SwingUtilities.invokeLater(() -> {
            String[] options = fileNames.toArray(new String[0]);
            String selected = (String) JOptionPane.showInputDialog(
                    mainWindow.getFrame(),
                    "Sélectionnez la sauvegarde à charger :",
                    "JEST - Charger une partie",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            int index = 0;
            if (selected != null) {
                index = fileNames.indexOf(selected);
            }
            future.complete(index);
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
