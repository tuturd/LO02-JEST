package JEST.cards;

import java.io.Serializable;
import java.util.List;

public class Offer implements Serializable {
    private List<OfferCard> cards;
    private boolean complete;

    public Offer(OfferCard c1, OfferCard c2) {
    	this.cards.add(0, c1);
    	this.cards.add(1, c2);
    }

    public List<OfferCard> getCards() {
        return this.cards;
    }

    public Card takeCard(boolean chooseFaceUp) {
        for (OfferCard offerCard : this.cards) {
        	if (offerCard.isUpside() == chooseFaceUp) {
        		this.cards.remove(offerCard);
        		return offerCard.getCard();
    		}
        }
        return null;
    }

    public Card takeCard() {
        OfferCard offerCard = this.cards.get(0);
        this.cards.remove(offerCard);
        return offerCard.getCard();
    }

    public OfferCard getFirstOfferCard() {
        return this.cards.get(0);
    }

    public void addOfferCard(OfferCard offerCard) { this.cards.add(offerCard); }

    public boolean isComplete() {
        return this.complete;
    }
}
