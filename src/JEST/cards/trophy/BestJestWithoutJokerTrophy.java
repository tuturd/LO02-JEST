package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.JokerCard;
import JEST.cards.Suit;

import java.io.Serializable;
import java.util.List;

/**
 * The winner is the player with the highest value Jest, but without the Joker.
 */
public class BestJestWithoutJokerTrophy implements Trophy, Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * Returns the winner of the trophy.
     * @param players all the players of the game.
     * @return the winner of the trophy.
     */
	public Player getWinner(List<Player> players) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;

        for (Player player : players) {
        	boolean testJestJoker = player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);
        	if (!testJestJoker) {
        		int total = player.getJest().getScore();

                /*for (Card card : player.getJest().getCards()) {
                    if (!(card instanceof JokerCard)) {
                        total += card.getFaceValue();
                    }
                }*/

                if (total > maxValue) {
                    maxValue = total;
                    winner = player;
                }
        	}
        }

        return winner;
    }

    public String getName() {
        return "Best Jest Without Joker Trophy";
    }
}
