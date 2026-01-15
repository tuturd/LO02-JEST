package JEST.controller;

import JEST.model.Player;
import JEST.model.cards.Card;

import java.util.List;

/**
 * Interface that defines all interactions between the game and the user.
 * This allows switching between console and graphical interface implementations.
 */
public interface GameController {

    /**
     * Ask the user to start a new game or load an existing one.
     * 
     * @return 1 for new game, 2 for load game
     */
    int askNewOrLoadGame();

    /**
     * Ask for the number of players.
     * 
     * @return the number of players (3 or 4)
     */
    int askNumberOfPlayers();

    /**
     * Ask if a player is virtual.
     * 
     * @param playerNumber the player number
     * @return true if virtual, false otherwise
     */
    boolean askIsVirtualPlayer(int playerNumber);

    /**
     * Ask for the first name of a player.
     * 
     * @param playerNumber the player number
     * @return the first name
     */
    String askFirstName(int playerNumber);

    /**
     * Ask for the last name of a player.
     * 
     * @param playerNumber the player number
     * @return the last name
     */
    String askLastName(int playerNumber);

    /**
     * Ask for the strategy of a virtual player.
     * 
     * @param playerNumber the player number
     * @return "1" for random, "2" for defensive, "3" for aggressive
     */
    String askStrategy(int playerNumber);

    /**
     * Display a message about player creation.
     * 
     * @param playerNumber the player number
     * @param firstName the first name
     * @param lastName the last name
     * @param isVirtual true if virtual player
     */
    void displayPlayerCreated(int playerNumber, String firstName, String lastName, boolean isVirtual);

    /**
     * Display the trophy cards.
     * 
     * @param trophyCards the trophy cards
     */
    void displayTrophyCards(List<Card> trophyCards);

    /**
     * Ask a player to make an offer.
     * 
     * @param player the player making the offer
     * @param jest the player's current jest
     * @param c1 first card
     * @param c2 second card
     * @return the index of the card to show (1 or 2)
     */
    int askMakeOffer(Player player, String jest, Card c1, Card c2);

    /**
     * Ask a player to choose an offer.
     * 
     * @param player the player choosing
     * @param jest the player's current jest
     * @param availablePlayers the list of players with available offers
     * @return an array with [playerIndex, cardChoice] where cardChoice is 1 for face-up, 2 for face-down
     */
    int[] askChooseOffer(Player player, String jest, List<Player> availablePlayers);

    /**
     * Display the card added to a player's jest.
     *
     * @param player the player
     * @param card   the card added
     */
    void displayCardAddedToJest(Player player, Card card);

    /**
     * Display the final ranking and winner.
     *
     * @param ranking the ranking as a string
     */
    void displayWinner(String ranking);

    /**
     * Ask if the user wants to save the game.
     *
     * @return true to save, false otherwise
     */
    boolean askSaveGame();

    /**
     * Ask for the save file name.
     *
     * @return the file name
     */
    String askSaveFileName();

    /**
     * Display a message.
     *
     * @param message the message to display
     * @param type    the type of message (NORMAL, INFORMATION, ERROR)
     */
    void displayMessage(String message, MessageType type);

    /**
     * Ask the user to select a file to load.
     *
     * @param fileNames the list of available files
     * @return the index of the selected file
     */
    int askSelectFileToLoad(List<String> fileNames);

    /**
     * Message type for display.
     */
    enum MessageType {
        NORMAL,
        INFORMATION,
        ERROR
    }
}
