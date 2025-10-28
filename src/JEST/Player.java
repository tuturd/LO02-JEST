package JEST;

import JEST.cards.Card;
import JEST.cards.Jest;
import JEST.cards.Offer;
import JEST.cards.OfferCard;

import java.util.List;

public class Player {
    private final String firstName;
    private final String lastName;
    private Jest jest = new Jest();
    private Offer currentOffer;

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void makeOffer(Card c1, Card c2) {
        OfferCard offerCard1 = new OfferCard(c1, true);
        OfferCard offerCard2 = new OfferCard(c2, false);
        currentOffer = new Offer(offerCard1, offerCard2);
    }

    public void chooseOffer(List<Player> players) {

    }

    public void drawCard() {}

    public Jest getJest() { return jest; }
    public Offer getCurrentOffer() { return currentOffer; }

    @Override
    public String toString() { return String.format("%s %s", firstName, lastName); }
}
