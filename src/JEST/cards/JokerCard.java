package JEST.cards;

import JEST.cards.trophy.Trophy;

import java.io.Serializable;

public class JokerCard extends Card {

    public JokerCard(Trophy trophy) {
        super(trophy);
    }

    @Override
    public int getFaceValue() {
        return 0;
    }

    @Override
    public Suit getSuit() {
        return Suit.JOKER;
    }

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Joker";
    }
}



