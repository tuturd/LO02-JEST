package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Suit;

import java.io.Serializable;
import java.util.List;

public class HighestInSuitTrophy implements Trophy, Serializable {
    private final Suit suit;

    public HighestInSuitTrophy(Suit suit) {
        this.suit = suit;
    }

    @Override
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int maxValue = Integer.MIN_VALUE;

        for (Player player : players) {
            for (Card card : player.getJest().getCards()) {
                if (card.getSuit() == this.suit && card.getFaceValue() > maxValue) {
                    maxValue = card.getFaceValue();
                    winner = player;
                }
            }
        }
        return winner;
    }

    @Override
    public String getName() {
        return "Highest " + suit + " Trophy";
    }
}
