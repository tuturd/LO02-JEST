package JEST.cards;

public interface Card {
    void accept(CardVisitor visitor);
    int getFaceValue();
    Suit getSuit();
}
