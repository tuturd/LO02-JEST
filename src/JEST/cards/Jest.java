package JEST.cards;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * This class defines the list of {@link Card}s that the {@link Player} detains during all the game. In each round, he adds an {@link OfferCard} that he chooses to his jest.
 */
public class Jest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final List<Card> cards;
    
    /**
     * At the beginning of the game, the player's jest is empty.
     */
    public Jest() {
        this.cards = new ArrayList<>();
    }
    
    /**
     * The player adds a card to his jest.
     * @param card is the card that the player wants to add to his jest.
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    public List<Card> getCards() {
        return this.cards;
    }
    
    /**
     * We get the score of the player, using the {@link ScoreVisitor}.
     * @return the score of the player (the value of his Jest).
     */
    public int getScore() {
        ScoreVisitor scoreVisitor = new ScoreVisitor(this);
        scoreVisitor.compute();
        return scoreVisitor.getScore();
    }

	public String toString() {
		return "" + cards;
	}
}
