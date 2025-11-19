package JEST.cards;

import JEST.cards.trophy.Trophy;

import java.io.Serializable;

public class SuitCard extends Card {

    private final Suit suit;
    private int faceValue;

    public SuitCard(Suit suit, int faceValue, Trophy trophy) {
        super(trophy);
        this.suit = suit;
        this.faceValue = faceValue;
    }

    @Override
    public int getFaceValue() {
        return faceValue;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    public void transformToFive() {
        this.faceValue = 5;
    }
}

