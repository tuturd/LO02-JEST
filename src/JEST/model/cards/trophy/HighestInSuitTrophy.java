package JEST.model.cards.trophy;

import JEST.model.Player;
import JEST.model.cards.Card;
import JEST.model.cards.Suit;

import java.io.Serializable;
import java.util.List;

/**
 * The winner is the player with highest face value card in this suit.
 */
public class HighestInSuitTrophy implements Trophy, Serializable {
    private static final long serialVersionUID = 1L;
    private final Suit suit;

    /**
     * We define the suit associated to the trophy.
     *
     * @param suit suit associated to the trophy.
     */
    public HighestInSuitTrophy(Suit suit) {
        this.suit = suit;
    }

    /**
     * We see who have the highest face value in the suit of the trophy.
     *
     * @param players all the players of the game.
     * @return the winner of the trophy.
     */
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;

        for (Player player : players) {
            for (Card card : player.getJest().getCards()) {
                if (card.getSuit() == this.suit && card.getFaceValue() > maxValue) {
                    maxValue = card.getFaceValue();
                    winner = player;
                }
            }
        }
        return winner;
    }

    public String getName() {
        return "Highest " + suit + " Trophy";
    }
}
