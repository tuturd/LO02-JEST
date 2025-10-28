package JEST.cards;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ScoreVisitor implements CardVisitor, Serializable {
    private final List<Card> hand;
    private int score = 0;

    public ScoreVisitor(List<Card> hand) {
        this.hand = hand;
    }

    public int compute() {
        return 0;
    }

    private int computeBlackPairsBonus(List<SuitCard> spades, List<SuitCard> clubs, Map<Card,Integer> valMap) {
        return 0;
    }

    public void visit(SuitCard card) {}
    public void visit(JokerCard joker) {}
    }

