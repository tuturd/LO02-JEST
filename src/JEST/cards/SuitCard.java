package JEST.cards;

import java.io.Serializable;

public class SuitCard implements Card, Serializable {
    private final Suit suit;
    private final int faceValue;

    public SuitCard(Suit suit1, int faceValue1) {
        this.suit = suit1;
        this.faceValue = faceValue1;
    }

    public int getFaceValue() { return faceValue; }
    public Suit getSuit() { return suit; }
    public void accept(CardVisitor visitor) { visitor.visit(this); }

    public String toString() {
        return suit.toString() + " " + faceValue;
    }
}

