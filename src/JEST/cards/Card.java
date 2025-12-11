package JEST.cards;
import JEST.cards.trophy.Trophy;

import java.io.Serializable;

/**
 * This is a general card : we define the trophy of the card, and declare abstract the methods to get the face value and the suit (we define them if it is a suitcard).
 */
public abstract class Card implements Serializable {
    private final Trophy trophy;

    protected Card(Trophy trophy) {
        this.trophy = trophy;
    }

    public abstract int getFaceValue();
    public abstract Suit getSuit();

    /**
     * Accept the visitor.
     * @param visitor
     */
    public abstract void accept(CardVisitor visitor);

    public Trophy getTrophy() {
        return trophy;
    }

    @Override
    public String toString() {
        return getSuit() + " " + getFaceValue();
    }
}
