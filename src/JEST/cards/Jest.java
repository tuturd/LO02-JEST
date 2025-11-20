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
        this.cards.add(card);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getScore() {
        ScoreVisitor scoreVisitor = new ScoreVisitor(this);
        scoreVisitor.compute();
        return scoreVisitor.getScore();
    }
}
