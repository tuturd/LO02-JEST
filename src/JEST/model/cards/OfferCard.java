package JEST.model.cards;

import java.io.Serializable;

/**
 * An offerCard is a {@link Card} in an offer. It is face-up or face-down.
 */
public class OfferCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Card card;
    private final boolean upside;

    /**
     * We define the offerCard by a card, and if it is upside.
     *
     * @param card   the card concerned.
     * @param upside to know if the card is face-up or face-down.
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
