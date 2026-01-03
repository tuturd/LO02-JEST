package JEST;

import JEST.controller.ConsoleController;
import JEST.controller.GameController;
import JEST.controller.PlayerRouter;
import JEST.model.Game;
import JEST.view.MainWindow;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Ask in console if user wants to start GUI
        Scanner scanner = new Scanner(System.in);
        System.out.println("===========================================");
        System.out.println("         Bienvenue dans JEST !             ");
        System.out.println("===========================================");
        System.out.println();
        System.out.println("Voulez-vous démarrer l'interface graphique ?");
        System.out.println("1. Oui - Lancer l'interface graphique");
        System.out.println("2. Non - Jouer en mode console uniquement");
        System.out.print("Votre choix : ");

        int choice = 2;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Choix invalide, mode console par défaut.");
        }

        if (choice == 1) {
            startGUIMode();
        } else {
            startConsoleMode();
        }
    }

    /**
     * Start the game in console mode only.
     */
    private static void startConsoleMode() {
        MainConsole.main(new String[]{});
    }

    /**
     * Start the game in GUI mode with player-aware interface.
     * Each player can choose their preferred interface (GUI or console).
     */
    private static void startGUIMode() {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            GameController guiController = mainWindow.getGameController();
            GameController consoleController = new ConsoleController();

            PlayerRouter playerRouter = new PlayerRouter(guiController, consoleController);

            final Game[] game = {Game.getInstance()};
            game[0].setGameController(guiController);
            game[0].setPlayerRouter(playerRouter);

            Thread gameThread = new Thread(() -> {
                while (true) {

                    int choice = guiController.askNewOrLoadGame();

                    if (choice == 1) {
                        try {
                            while (game[0].getPlayers().isEmpty() || game[0].getTrophyCards().isEmpty()) {
                                Thread.sleep(500);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (choice == 2) {
                        Game loadedGame = Game.load(guiController);
                        if (loadedGame == null) {
                            guiController.displayMessage("Erreur lors du chargement ou aucune sauvegarde trouvée.", GameController.MessageType.ERROR);
                            continue;
                        }
                        Game.destroyInstance();
                        game[0] = loadedGame;
                        game[0].setPlayerRouter(playerRouter);

                        if (!game[0].getTrophyCards().isEmpty()) {
                            guiController.displayTrophyCards(game[0].getTrophyCards());
                        }
                        guiController.displayMessage("Partie chargée avec succès !", GameController.MessageType.INFORMATION);
                    }

                    boolean gameIsEnded = false;
                    while (!gameIsEnded) {
                        game[0].playRound();
                        gameIsEnded = game[0].endGameIfNecessary();
                        if (!gameIsEnded) {
                            game[0].suggestSaving();
                        }
                    }
                }
            });
            gameThread.start();
        });
    }
}