package JEST.model.cards;

import JEST.model.cards.trophy.Trophy;

/**
 * It defines a {@link Card} with a {@link Suit}.
 */
public class SuitCard extends Card {

    private static final long serialVersionUID = 1L;
    private final Suit suit;
    private int faceValue;

    /**
     * A suitcard is defined by its suit, its faceValue and its {@link Trophy}.
     *
     * @param suit      the suit of the card.
     * @param faceValue the face value of the card.
     * @param trophy    the trophy associated to the card.
     */
    public SuitCard(Suit suit, int faceValue, Trophy trophy) {
        super(trophy);
        this.suit = suit;
        this.faceValue = faceValue;
    }

    public int getFaceValue() {
        return this.faceValue;
    }

    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Accept the visitor.
     *
     * @param visitor get the score.
     */
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Transform the face value into 5.
     */
    public void transformToFive() {
        this.faceValue = 5;
    }

    public String getPicturePath() {
        return "/JEST/static/" + this.suit + "_" + this.faceValue + ".png";
    }
}

