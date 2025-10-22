package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;
import JEST.cards.Card;
import JEST.cards.Suit;

import java.util.List;

public class HighestSpadeTrophy implements Trophy {
    public Player getWinner(List<Jest> jests) {
        Player winner = null;
        int maxValue = -1;
        for (Jest jest : jests) {
            for (Card card : jest.getCards()) {
                if (card.getSuit() == Suit.SPADE && card.getFaceValue() > maxValue) {
                    maxValue = card.getFaceValue();
                    winner = jest.getOwner();
                }
            }
        }
        return winner;
    }
    public String getName() { return "Highest Spade"; }
}
