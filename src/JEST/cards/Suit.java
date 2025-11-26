package JEST.cards;

public enum Suit {
    SPADE(4), CLUB(3), DIAMOND(2), HEART(1), JOKER(0);

    private final int strength;

    Suit(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
