package JEST.cards;

import JEST.cards.trophy.Trophy;

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
        return this.faceValue;
    }

    @Override
    public Suit getSuit() {
        return this.suit;
    }

    @Override
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    public void transformToFive() {
        this.faceValue = 5;
    }
}

