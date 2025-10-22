package JEST.virtualPlayer;

import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.Player;
import JEST.cards.Offer;

public interface Strategy {
    void makeOffer(Card c1, Card c2);
    void chooseOffer(Offer offer);
}