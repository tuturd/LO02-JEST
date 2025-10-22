package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;
import JEST.cards.Card;
import JEST.cards.JokerCard;

import java.util.List;

public class JokerTrophy implements Trophy {

    public Player getWinner(List<Jest> jests) {
        for (Jest jest : jests) {
            for (Card card : jest.getCards()) {
                if (card instanceof JokerCard) {
                    return jest.getOwner();
                }
            }
        }
        return null;
    }

    public String getName() {
        return "Joker Trophy";
    }
}
