package JEST.model.cards;

/**
 * Define each suit and its strength.
 */
public enum Suit {
    SPADE(4), CLUB(3), DIAMOND(2), HEART(1), JOKER(0);

    private final int strength;

    /**
     * Constructor of the suit.
     *
     * @param strength the strength of the suit.
     */
    Suit(int strength) {
        this.strength = strength;
    }

    /**
     * Get the strength of the suit.
     *
     * @return the strength.
     */
    public int getStrength() {
        return strength;
    }
}
