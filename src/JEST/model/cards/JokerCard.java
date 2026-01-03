package JEST.model.cards;

import JEST.model.cards.trophy.Trophy;

/**
 * There is one joker among the cards. It is a special {@link Card} because it can add or remove points in your {@link Jest}.
 */
public class JokerCard extends Card {
    private static final long serialVersionUID = 1L;

    /**
     * The joker card is associated to a {@link Trophy}.
     *
     * @param trophy is the trophy associated to the card.
     */
    public JokerCard(Trophy trophy) {
        super(trophy);
    }

    /**
     * By default, the face value of the joker is 0.
     */
    public int getFaceValue() {
        return 0;
    }

    public Suit getSuit() {
        return Suit.JOKER;
    }

    /**
     * Accept the visitor.
     *
     * @param visitor to get the score.
     */
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return "Joker";
    }

    public String getPicturePath() {
        return "/JEST/static/joker.png";
    }
}



