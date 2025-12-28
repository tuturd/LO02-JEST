package JEST;

import JEST.model.Game;
import JEST.view.GraphicalInterface;
import JEST.controller.GameController;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            Game game = Game.getInstance();
            GraphicalInterface view = new GraphicalInterface();
            GameController controller = new GameController(game, view);
            view.setVisible(true);
        });

        Thread consoleThread = new Thread(() -> {
            MainConsole.main(args);
        });

        consoleThread.start();
    }
}
