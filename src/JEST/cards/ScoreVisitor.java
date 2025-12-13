package JEST.cards;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScoreVisitor implements CardVisitor, Serializable {
    private final List<Card> cards;
    private int score = 0;
    private int jokerScore = 0;

    public ScoreVisitor(Jest jest) {
        this.cards = jest.getCards();
    }

    public void compute() {
        for  (Card card : this.cards) {
            card.accept(this);
        }
    }

    public void visit(SuitCard card) {
        acesTransformation(card);

        Suit suit = card.getSuit();
        int value = card.getFaceValue();

        switch (suit) {
            case SPADE, CLUB -> {
                this.score += value + computeBlackPairsBonus();
            }
            case DIAMOND -> {
                this.score -= value;
            }
        }
    }

    public void visit(JokerCard joker) {
        List<Card> heartCards = cards.stream()
                .filter(card -> card.getSuit() == Suit.HEART)
                .collect(Collectors.toList());

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

    private void acesTransformation(Card card) {
        if (card.getFaceValue() == 1) {
            boolean aloneInSuit = this.cards.stream()
                    .noneMatch(element -> element != card && element.getSuit() == card.getSuit());
            if (aloneInSuit && card instanceof SuitCard suitCard) {
                suitCard.transformToFive();
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
}

