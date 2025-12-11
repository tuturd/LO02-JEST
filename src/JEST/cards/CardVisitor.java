package JEST.cards;

/**
 * This interface allows access to the scores obtained by the suit cards or the joker card.
 */
public interface CardVisitor {
    void visit(SuitCard card);
    void visit(JokerCard joker);
}


