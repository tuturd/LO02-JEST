package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.JokerCard;

import java.io.Serializable;
import java.util.List;

/**
 * The winner is the {@link Player} with the Joker.
 */
public class JokerTrophy implements Trophy, Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * We see who is the player with the Joker in his Jest.
     * @param players all the players of the game.
     * @return the winner of the trophy.
     */
	public Player getWinner(List<Player> players) {
        for (Player player : players) {
            for (Card card : player.getJest().getCards()) {
                if (card instanceof JokerCard) {
                    return player;
                }
            }
        }
        return null;
    }
    
    public String getName() {
        return "Joker Trophy";
    }
}
