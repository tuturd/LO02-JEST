package JEST;

import JEST.model.Game;
import JEST.view.NewGameCreation;
import JEST.view.PartyStartInterface;
import JEST.controller.GameController;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            Game game = Game.getInstance();
            PartyStartInterface partyStartView = new PartyStartInterface();
            GameController controller = new GameController(game, partyStartView);
            partyStartView.setVisible(true);
        });

        Thread consoleThread = new Thread(() -> {
            MainConsole.main(args);
        });

        consoleThread.start();
    }
}
