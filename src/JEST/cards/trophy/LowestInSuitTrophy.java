package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Suit;

import java.io.Serializable;
import java.util.List;

/**
 * The winner is the player with the lowest face value card in this suit.
 */
public class LowestInSuitTrophy implements Trophy, Serializable {
	private static final long serialVersionUID = 1L;
	private final Suit suit;

	/**
     * We define the suit associated to the trophy.
     * @param suit suit associated to the trophy.
     */
	public LowestInSuitTrophy(Suit suit) {
        this.suit = suit;
    }

	/**
     * Returns the winner of the trophy.
     * @param players all the players of the game.
     * @return the winner of the trophy.
     */
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int minValue = Integer.MAX_VALUE;

        for (Player player : players) {
            for (Card card : player.getJest().getCards()) {
                if (card.getSuit() == this.suit && card.getFaceValue() < minValue) {
                    minValue = card.getFaceValue();
                    winner = player;
                }
            }
        }
        return winner;
    }

    @Override
    public String getName() {
        return "Lowest " + suit + " Trophy";
    }
}