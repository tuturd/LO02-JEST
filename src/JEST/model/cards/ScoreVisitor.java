package JEST.model.cards;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Compute the score of each {@link Jest}.
 */
public class ScoreVisitor implements CardVisitor, Serializable {
    private static final long serialVersionUID = 1L;
	
	private final List<Card> cards;
    private int score = 0;
    private int jokerScore = 0;
    private Phase phase = null;

    /**
     * Compute the score of the Jest announced.
     */
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

    /**
     * Attribute to each {@link Suit} the score of the card.
     * @param card is the {@link Card} which we compute the score.
     */
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

    /**
     * If there is no heart, the {@link JokerCard} vaults 4 points.
     * If there is 1-3 hearts, the joker vaults the sum of each Heart's face value.
     * @param joker the joker card.
     */
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

    /**
     * If the Ace is the only card of that suit in the Jest, the card becomes a 5, with a face value of 5.
     * Otherwise it remains an Ace, with a face value of 1.
     * @param card the card concerned.
     */
    private void acesTransformation(SuitCard card) {
        if (card.getFaceValue() == 1) {
            boolean aloneInSuit = this.cards.stream()
                    .noneMatch(element -> element != card && element.getSuit() == card.getSuit());
            if (aloneInSuit) {
                card.transformToFive();
            }
        }
    }

    /**
     * If there is a Spade and a Club with the same face value, the pair is worth a bonus 2 points in addition to the face values of the cards.
     * @return the bonus.
     */
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

    /**
     * Distinguish 2 phases : preparation and score.
     */
    private enum Phase {
    	/**
    	 * Do the aces transformation if it is necessary.
    	 */
    	PREPARE, 
    	/**
    	 * Compute the score of each card.
    	 */
    	SCORE
    	}
}
