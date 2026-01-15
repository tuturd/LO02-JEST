package JEST;

import JEST.controller.ConsoleController;
import JEST.controller.GameController;
import JEST.controller.PlayerRouter;
import JEST.model.Game;

/**
 * The class allows the game to run and the player to use the console.
 */
public class MainConsole {
    private static MainConsole instance;
    private Game game;
    private GameController gameController;

    /**
     * Private constructor for Singleton pattern.
     */
    private MainConsole() {
        this.game = Game.getInstance();
        this.gameController = new ConsoleController();
        this.game.setGameController(this.gameController);

        // In console-only mode, all players use console, so router points always to console
        PlayerRouter playerRouter = new PlayerRouter(this.gameController, this.gameController);
        this.game.setPlayerRouter(playerRouter);
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
     * Launch the game and allows the player to use the console (if he chooses the console mode).
     */
    public static void main(String[] args) {
        MainConsole main = MainConsole.getInstance();
        Game game = main.getGame();
        GameController gameController = main.gameController;

        while (true) {
            int choice = gameController.askNewOrLoadGame();

            if (choice == 1) {
                game = main.renewGame();
                game.setGameController(gameController);
                game.setup();
            } else if (choice == 2) {
                gameController.displayMessage("Chargement de la partie > En cours...", GameController.MessageType.NORMAL);
                game = Game.load(gameController);
                if (game == null) {
                    continue; // retry
                }
                main.game = game;
                PlayerRouter playerRouter = new PlayerRouter(gameController, gameController);
                game.setPlayerRouter(playerRouter);
                gameController.displayMessage("Chargement de la partie > OK", GameController.MessageType.INFORMATION);
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
        this.game = Game.getInstance();
        this.game.setGameController(this.gameController);
        PlayerRouter playerRouter = new PlayerRouter(this.gameController, this.gameController);
        this.game.setPlayerRouter(playerRouter);
        return this.game;
    }
}
