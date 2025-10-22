package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;
import JEST.cards.Card;
import JEST.cards.Suit;

import java.util.List;

public class LowestFaceValueTrophy implements Trophy {
    private final Suit suit;

    public LowestFaceValueTrophy(Suit suit) {
        this.suit = suit;
    }

    public Player getWinner(List<Jest> jests) {
        Player winner = null;
        int minValue = Integer.MAX_VALUE;
        for (Jest jest : jests) {
            for (Card card : jest.getCards()) {
                if (card.getSuit() == suit && card.getFaceValue() < minValue) {
                    minValue = card.getFaceValue();
                    winner = jest.getOwner();
                }
            }
        }
        return winner;
    }

    public String getName() {
        return "Lowest " + suit + " Trophy";
    }
}