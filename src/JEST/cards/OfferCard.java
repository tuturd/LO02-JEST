package JEST.cards;

public class OfferCard {
    private final Card card;
    private final boolean upside;

    public OfferCard(Card card, boolean upside) {
        this.card = card;
        this.upside = upside;
    }

    public Card getCard() {
        return card;
    }

    public boolean isUpside() {
        return upside;
    }
}
