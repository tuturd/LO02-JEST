package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;

import java.io.Serializable;
import java.util.List;

/**
 * The winner is the player with the highest value Jest.
 */
public class BestJestTrophy implements Trophy, Serializable {
	private static final long serialVersionUID = 1L;
	
	
	public Player getWinner(List<Player> players) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;

        for (Player player : players) {
            int total = player.getJest().getScore();

            
            /*for (Card card : player.getJest().getCards()) {
                total += card.getFaceValue();	
            }*/

            if (total > maxValue) {
                maxValue = total;
                winner = player;
            }
        }

        return winner;
    }

    public String getName() {
        return "Best Jest Trophy";
    }
}
