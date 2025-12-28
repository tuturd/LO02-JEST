package JEST;

import JEST.model.Game;

import java.util.Scanner;

/**
 * The class allows the game to run and the player to use the console.
 */
public class MainConsole {
    private static MainConsole instance;
    private Game game;

    private MainConsole() {
        this.game = Game.getInstance();
    }

    /**
     * We use the Singleton design pattern, to guarantee that there is only one instance of this class.
     * @return the only instance of the class.
     */
    public static MainConsole getInstance() {
        if (instance == null) {
            instance = new MainConsole();
        }
        return instance;
    }

    /**
     * This class is one of the most important : it is the one that truly launches the game and allows the player to use the console.
     */
    public static void main(String[] args) {
        MainConsole main = MainConsole.getInstance();
        Game game = main.getGame();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Voulez-vous commencer une nouvelle partie (1) ou charger une partie existante (2) : ");
            String nom = scanner.nextLine();
            System.out.println();

            if (nom.equals("1")) {
                game = main.renewGame();
                game.setup();
            } else if (nom.equals("2")) {
                System.out.println("Chargement de la partie > En cours...");
                game = Game.load();
                System.out.println("Chargement de la partie > OK");
            } else {
                System.out.println("Entrée invalide. Veuillez entrer 1 ou 2.");
            }

            boolean gameIsEnded = false;
            boolean gameIsSaved = false;
            while (!gameIsEnded && !gameIsSaved) {
            	game.playRound();
            	gameIsEnded = game.endGameIfNecessary();
                if (!gameIsEnded) {
                    gameIsSaved = game.suggestSaving();
                }
            }
        }
    }

    public Game getGame() {
        return this.game;
    }
    
    /**
     * Create a new game, destroying the old one.
     * @return the new game.
     */
    public Game renewGame() {
        Game.destroyInstance();
        return Game.getInstance();
    }
}
