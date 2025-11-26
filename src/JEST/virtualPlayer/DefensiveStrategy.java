package JEST.virtualPlayer;

import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.Player;
import JEST.cards.Offer;

import java.io.Serializable;
import java.util.List;

public class DefensiveStrategy implements Strategy, Serializable {
    @Override
    public void makeOffer(Card c1, Card c2) {}

    @Override
    public void chooseOffer(List<Player> players) {}
}