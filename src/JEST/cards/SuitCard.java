package JEST.cards;

public class SuitCard implements Card {
    private final Suit suit;
    private final int faceValue;

    public SuitCard(Suit suit1, int faceValue1) {
        this.suit = suit1;
        this.faceValue = faceValue1;
    }

    public int getFaceValue() { return faceValue; }
    public Suit getSuit() { return suit; }
    public void accept(CardVisitor visitor) { visitor.visit(this); }
}

