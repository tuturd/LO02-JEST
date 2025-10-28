package JEST.cards;

import java.io.Serializable;

public class JokerCard implements Card, Serializable {
    public JokerCard() {}
    public int getFaceValue() { return 0; }
    public Suit getSuit() { return Suit.JOKER; }
    public void accept(CardVisitor visitor) { visitor.visit(this); }

    public String toString() {
        return "Joker";
    }
}


