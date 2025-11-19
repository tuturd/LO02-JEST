package JEST.cards;

import JEST.Player;
import JEST.cards.trophy.Trophy;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Jest implements Serializable {
    private List<Card> cards;

    public Jest() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }


}
