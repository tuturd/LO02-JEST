package JEST.virtualPlayer;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.cards.Offer;

import java.io.Serializable;
import java.util.List;

public class VirtualPlayer extends Player implements Serializable {
    private Strategy strategy;

    public VirtualPlayer(String firstName, String lastName, Strategy strategy) {
        super(firstName, lastName);
        this.strategy = strategy;
    }

    public void makeOffer(Card c1, Card c2) {
        this.strategy.makeOffer(this, c1, c2);
    }

    public void chooseOffer(List<Player> players) {
        this.strategy.chooseOffer(this, players);
    }
}
