package JEST.cards;

import java.io.Serializable;

/**
 * An offercard is a {@link Card} in an offer. It is face-up or face-down.
 */
public class OfferCard implements Serializable {
    private final Card card;
    private final boolean upside;
    private static final long serialVersionUID = 1L;

    /**
     * We define the offercard by a card, and if it is upside.
     * @param card : the card concerned.
     * @param upside
     */
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
