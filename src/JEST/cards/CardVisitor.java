package JEST.cards;

public interface CardVisitor {
    void visit(SuitCard card);
    void visit(JokerCard joker);
}


