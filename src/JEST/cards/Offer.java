package JEST.cards;

import java.io.Serializable;
import java.util.List;

public class Offer implements Serializable {
    private List<OfferCard> cards;
    private boolean complete;

    public  Offer(OfferCard c1, OfferCard c2) {}

    public List<OfferCard> getCards() {
        return cards;
    }

    public Card takeCard(boolean chooseFaceUp) {
        return null;
    }

    public Card takeCard() {
        return null;
    }

    public boolean isComplete() {
        return complete;
    }
}
