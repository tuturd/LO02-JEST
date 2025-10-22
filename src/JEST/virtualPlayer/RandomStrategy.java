package JEST.virtualPlayer;

import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.Player;
import JEST.cards.Offer;

import java.util.Random;

public class RandomStrategy implements Strategy {
    @Override
    public void makeOffer(Card c1, Card c2) {
        Random rand = new Random();
    }

    @Override
    public void chooseOffer(Offer offer) {}
}