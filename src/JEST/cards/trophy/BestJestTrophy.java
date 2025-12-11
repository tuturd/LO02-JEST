package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;

import java.io.Serializable;
import java.util.List;

public class BestJestTrophy implements Trophy, Serializable {

    @Override
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;

        for (Player player : players) {
            int total = 0;

            for (Card card : player.getJest().getCards()) {
                total += card.getFaceValue();
            }

            if (total > maxValue) {
                maxValue = total;
                winner = player;
            }
        }

        return winner;
    }

    @Override
    public String getName() {
        return "Best Jest Trophy";
    }
}
