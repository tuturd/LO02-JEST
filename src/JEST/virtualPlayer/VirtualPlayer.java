package JEST.virtualPlayer;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.cards.Offer;

import java.io.Serializable;

public class VirtualPlayer extends Player implements Serializable {
    private Strategy strategy;

    public VirtualPlayer(String firstName, String lastName, Strategy strategy) {
        super(firstName, lastName);
        this.strategy = strategy;
    }

    public void makeOffer(Card c1, Card c2) {
        strategy.makeOffer(c1, c2);
    }

    public void chooseOffer(Offer offer) {
        strategy.chooseOffer(offer);
    }
}
