package JEST.cards;

public class JokerCard implements Card {
    public JokerCard() {}
    public int getFaceValue() { return 0; }
    public Suit getSuit() { return Suit.JOKER; }
    public void accept(CardVisitor visitor) { visitor.visit(this); }
}


