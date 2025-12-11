package JEST.cards;

import JEST.cards.trophy.Trophy;

/**
 * There is one joker among the cards. It is a special card because it can add or remove points in your Jest.
 */
public class JokerCard extends Card {

    /**
     * The JokerCard is associated to a {@link Trophy}.
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
     * @param visitor
     */
    public void accept(CardVisitor visitor) {
        visitor.visit(this);
    }

    public String toString() {
        return "Joker";
    }
}



