package JEST.model.cards;

import JEST.model.cards.trophy.Trophy;

import java.io.Serializable;

/**
 * This is a general card : we define the {@link Trophy} of the card, and declare abstract the methods to get the face value and the suit (we define them if it is a {@link SuitCard}.
 */
public abstract class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Trophy trophy;

    /**
     * A card is defined by its {@link Trophy}.
     *
     * @param trophy the trophy associated to the card.
     */
    protected Card(Trophy trophy) {
        this.trophy = trophy;
    }

    /**
     * Get the face value of the card.
     *
     * @return the face value
     */
    public abstract int getFaceValue();

    /**
     * Get the suit of the card.
     *
     * @return the suit
     */
    public abstract Suit getSuit();

    /**
     * Accept the visitor.
     *
     * @param visitor to get the score.
     */
    public abstract void accept(CardVisitor visitor);

    public Trophy getTrophy() {
        return trophy;
    }

    public String toString() {
        return getSuit() + " " + getFaceValue();
    }

    /**
     * Get the picture path of the card.
     *
     * @return the picture path
     */
    public abstract String getPicturePath();
}
