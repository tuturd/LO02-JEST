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

    public GUIController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Complete a pending integer response.
     */
    public void completeIntResponse(int value) {
        if (pendingIntResponse != null) {
            pendingIntResponse.complete(value);
        }
    }

    /**
     * Complete a pending string response.
     */
    public void completeStringResponse(String value) {
        if (pendingStringResponse != null) {
            pendingStringResponse.complete(value);
        }
    }

    /**
     * Complete a pending boolean response.
     */
    public void completeBooleanResponse(boolean value) {
        if (pendingBooleanResponse != null) {
            pendingBooleanResponse.complete(value);
        }
    }

    /**
     * Complete a pending int array response.
     */
    public void completeIntArrayResponse(int[] value) {
        if (pendingIntArrayResponse != null) {
            pendingIntArrayResponse.complete(value);
        }
    }

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

    @Override
    public void displayPlayerCreated(int playerNumber, String firstName, String lastName, boolean isVirtual) {
        String message = isVirtual
                ? String.format(">>> Joueur virtuel n°%d créé", playerNumber)
                : String.format(">>> Joueur n°%d créé : %s %s", playerNumber, firstName, lastName);
        mainWindow.addLog(message);
    }

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

    @Override
    public void displayCardAddedToJest(Player player, Card card) {
        mainWindow.addLog(player + " a ajouté à son Jest : " + card);
    }

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
