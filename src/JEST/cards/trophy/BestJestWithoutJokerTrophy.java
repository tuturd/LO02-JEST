package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;
import JEST.cards.Card;
import JEST.cards.JokerCard;

import java.util.List;

public class BestJestWithoutJokerTrophy implements Trophy {

    public Player getWinner(List<Jest> jests) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;
        for (Jest jest : jests) {
            int total = 0;
            for (Card card : jest.getCards()) {
                if (!(card instanceof JokerCard)) {
                    total += card.getFaceValue();
                }
            }
            if (total > maxValue) {
                maxValue = total;
                winner = jest.getOwner();
            }
        }
        return winner;
    }

    public String getName() {
        return "Best Jest Without Joker Trophy";
    }
}
