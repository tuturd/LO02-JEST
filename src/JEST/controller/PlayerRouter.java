package JEST.controller;

import JEST.model.Player;
import JEST.model.cards.Card;

import java.util.List;

/**
 * Lightweight router that delegates player-specific operations to the appropriate controller
 * based on the player's preferred interface (GUI or Console).
 * <p>
 * This class only handles the 3 operations that depend on individual player preferences:
 * - askMakeOffer
 * - askChooseOffer
 * - displayCardAddedToJest
 * <p>
 * All other game operations (setup, save, global displays) should use the main GameController directly.
 */
public class PlayerRouter {

    private final GameController guiController;
    private final GameController consoleController;

    /**
     * Create a player router with GUI and Console controllers.
     *
     * @param guiController     the controller for GUI interactions
     * @param consoleController the controller for console interactions
     */
    public PlayerRouter(GameController guiController, GameController consoleController) {
        this.guiController = guiController;
        this.consoleController = consoleController;
    }

    /**
     * Get the appropriate controller based on player's preferred interface.
     *
     * @param player the player whose preference to check
     * @return the GUI or Console controller
     */
    private GameController getControllerForPlayer(Player player) {
        if (player.getPreferredInterface() == Player.InterfaceType.GUI) {
            return guiController;
        }
        return consoleController;
    }

    /**
     * Ask a player to make an offer, using their preferred interface.
     *
     * @param player the player making the offer
     * @param jest   the player's current jest
     * @param c1     first card
     * @param c2     second card
     * @return the index of the card to show face up (1 or 2)
     */
    public int askMakeOffer(Player player, String jest, Card c1, Card c2) {
        return getControllerForPlayer(player).askMakeOffer(player, jest, c1, c2);
    }

    /**
     * Ask a player to choose an offer, using their preferred interface.
     *
     * @param player           the player choosing
     * @param jest             the player's current jest
     * @param availablePlayers players with available offers
     * @return array with [playerIndex, cardChoice]
     */
    public int[] askChooseOffer(Player player, String jest, List<Player> availablePlayers) {
        return getControllerForPlayer(player).askChooseOffer(player, jest, availablePlayers);
    }

    /**
     * Display that a card was added to a player's jest, using their preferred interface.
     *
     * @param player the player who received the card
     * @param card   the card that was added
     */
    public void displayCardAddedToJest(Player player, Card card) {
        getControllerForPlayer(player).displayCardAddedToJest(player, card);
    }
}
