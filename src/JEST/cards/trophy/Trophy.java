package JEST.cards.trophy;

import JEST.Player;

import java.util.List;

public interface Trophy {
    /**
     * Returns the winner of the trophy.
     * @param players all the players of the game.
     * @return the winner of the trophy.
     */
	Player getWinner(List<Player> players);
    
	String getName();
}

