package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Suit;

import java.io.Serializable;
import java.util.List;

public class LowestInSuitTrophy implements Trophy, Serializable {
    private final Suit suit;

    public LowestInSuitTrophy(Suit suit) {
        this.suit = suit;
    }

    @Override
    public Player getWinner(List<Player> players) {
        Player winner = null;
        int minValue = Integer.MAX_VALUE;

        for (Player player : players) {
            for (Card card : player.getJest().getCards()) {
                if (card.getSuit() == this.suit && card.getFaceValue() < minValue) {
                    minValue = card.getFaceValue();
                    winner = player;
                }
            }
        }
        return winner;
    }

    @Override
    public String getName() {
        return "Lowest " + suit + " Trophy";
    }
}