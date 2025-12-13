package JEST.cards;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScoreVisitor implements CardVisitor, Serializable {
    private final List<Card> cards;
    private int score = 0;
    private int jokerScore = 0;
    private Phase phase = null;

    public ScoreVisitor(Jest jest) {
        this.cards = jest.getCards();
    }

    public void compute() {
        this.phase = Phase.PREPARE;
        for (Card card : this.cards) {
            card.accept(this);
        }

        this.score += computeBlackPairsBonus();

        this.phase = Phase.SCORE;
        for (Card card : this.cards) {
            card.accept(this);
        }

        this.phase = null;
    }

    public void visit(SuitCard card) {
        if (this.phase == Phase.PREPARE) {
            acesTransformation(card);
            return;
        }

        if (this.phase == Phase.SCORE) {
            Suit suit = card.getSuit();
            int value = card.getFaceValue();

            switch (suit) {
                case SPADE, CLUB -> this.score += value;
                case DIAMOND -> this.score -= value;
                default -> {
                }
            }
        }
    }

    public void visit(JokerCard joker) {
        if (this.phase != Phase.SCORE) {
            return;
        }

        List<Card> heartCards = cards.stream()
                .filter(card -> card.getSuit() == Suit.HEART)
                .toList();

        int size = heartCards.size();

        if (size == 0) {
            this.jokerScore = 4;
        } else {
            int sum = heartCards.stream()
                    .mapToInt(Card::getFaceValue)
                    .sum();

            if (size <= 3) {
                this.jokerScore -= sum;
            } else {
                this.jokerScore += sum;
            }
        }

        this.score += this.jokerScore;
    }

    private void acesTransformation(SuitCard card) {
        if (card.getFaceValue() == 1) {
            boolean aloneInSuit = this.cards.stream()
                    .noneMatch(element -> element != card && element.getSuit() == card.getSuit());
            if (aloneInSuit) {
                card.transformToFive();
            }
        }
    }

    private int computeBlackPairsBonus() {
        Set<Integer> spadeFaceValues = this.cards.stream()
                .filter(card -> card.getSuit() == Suit.SPADE)
                .map(Card::getFaceValue)
                .collect(Collectors.toSet());
        Set<Integer> clubFaceValues = this.cards.stream()
                .filter(card -> card.getSuit() == Suit.CLUB)
                .map(Card::getFaceValue)
                .collect(Collectors.toSet());

        spadeFaceValues.retainAll(clubFaceValues);

        return spadeFaceValues.size() * 2;
    }

    public int getScore() {
        return score;
    }

    private enum Phase {PREPARE, SCORE}
}
