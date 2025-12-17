package JEST.cards;

/**
 * This interface allows access to the scores obtained by the {@link SuitCard}s or the {@link JokerCard}.
 */
public interface CardVisitor {
    void visit(SuitCard card);
    void visit(JokerCard joker);
}


