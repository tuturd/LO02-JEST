package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.JokerCard;

import java.util.List;

public class BestJestWithoutJokerTrophy implements Trophy {

    @Override
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;

        for (Player player : players) {
            int total = 0;

            for (Card card : player.getJest().getCards()) {
                if (!(card instanceof JokerCard)) {
                    total += card.getFaceValue();
                }
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
        return "Best Jest Without Joker Trophy";
    }
}
