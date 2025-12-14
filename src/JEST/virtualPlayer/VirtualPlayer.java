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
        System.out.printf("%s a fait une offre via une stratégie %s.\n", this, this.strategy);
        this.strategy.makeOffer(this, c1, c2);
    }

    public Player chooseOffer(List<Player> players) {
        System.out.printf("\n%s a choisi une offre via une stratégie %s.\n", this, this.strategy);
        return this.strategy.chooseOffer(this, players);
    }
}
