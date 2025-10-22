package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;
import JEST.cards.Card;

import java.util.List;

public class BestJestTrophy implements Trophy {

    public Player getWinner(List<Jest> jests) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;
        for (Jest jest : jests) {
            int total = 0;
            for (Card card : jest.getCards()) {
                total += card.getFaceValue();
            }
            if (total > maxValue) {
                maxValue = total;
                winner = jest.getOwner();
            }
        }
        return winner;
    }

    public String getName() {
        return "Best Jest Trophy";
    }
}
