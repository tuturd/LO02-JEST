package JEST.cards;

import JEST.Player;
import JEST.cards.trophy.Trophy;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Jest implements Serializable {
    private List<Card> cards;
    private List<Trophy> trophies;

    public Jest() {
        cards = new ArrayList<>();
        trophies = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Player getOwner() {
        return null;
    }

    public List<Card> getCards() {
        return cards;
    }


}
