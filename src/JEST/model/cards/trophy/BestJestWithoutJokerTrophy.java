package JEST.model.cards.trophy;

import JEST.model.Player;
import JEST.model.cards.Suit;

import java.io.Serializable;
import java.util.List;

/**
 * The winner is the player with the highest value Jest, but without the Joker.
 */
public class BestJestWithoutJokerTrophy implements Trophy, Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * We compute the Jest's value of each player (except the player with the Joker) to know who is the winner.
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
