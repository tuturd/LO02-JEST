package JEST.cards;

import java.io.Serializable;

public class OfferCard implements Serializable {
    private final Card card;
    private final boolean upside;

    public OfferCard(Card card, boolean upside) {
        this.card = card;
        this.upside = upside;
    }

    public Card getCard() {
        return this.card;
    }

    public boolean isUpside() {
        return this.upside;
    }
}
