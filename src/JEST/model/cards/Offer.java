package JEST.model.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * In each round, each {@link Player} have to make an offer and choose an other one.
 * An offer is composed by two {@link Card}s : a face-up and a face-down card.
 */
public class Offer implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<OfferCard> cards;

    /**
     * The offer is composed by two {@link OfferCard}.
     *
     * @param c1 is the first OfferCard.
     * @param c2 is the second OfferCard.
     */
    public Offer(OfferCard c1, OfferCard c2) {
        this.cards = new ArrayList<OfferCard>();
        this.cards.add(0, c1);
        this.cards.add(1, c2);
    }

    public List<OfferCard> getCards() {
        return this.cards;
    }

    /**
     * Get the face-up or the face-down card, as we choose.
     *
     * @param chooseFaceUp a boolean to know which card we get.
     * @return the card chosen.
     */
    public Card getCard(boolean chooseFaceUp) {
        for (OfferCard offerCard : this.cards) {
            if (offerCard.isUpside() == chooseFaceUp) {
                return offerCard.getCard();
            }
        }
        return null;
    }

    /**
     * Take the face-up or the face-down card, as we choose and remove it from the offer.
     *
     * @param chooseFaceUp a boolean to know which card we take.
     * @return the card chosen.
     */
    public Card takeCard(boolean chooseFaceUp) {
        for (OfferCard offerCard : this.cards) {
            if (offerCard.isUpside() == chooseFaceUp) {
                this.cards.remove(offerCard);
                return offerCard.getCard();
            }
        }
        return null;
    }

    /**
     * Take the first card of the player's offer.
     *
     * @return the remaining card.
     */
    public Card takeCard() {
        OfferCard offerCard = this.cards.get(0);
        this.cards.remove(offerCard);
        return offerCard.getCard();
    }

    public OfferCard getFirstOfferCard() {
        return this.cards.get(0);
    }

    public void addOfferCard(OfferCard offerCard) {
        this.cards.add(offerCard);
    }

    /**
     * If there are 2 cards in the offer, it is complete.
     *
     * @return true if the offer is complete.
     */
    public boolean isComplete() {
        return this.cards.size() == 2;
    }
}