package JEST.virtualPlayer;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Offer;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy, Serializable {
    @Override
    public void makeOffer(Card c1, Card c2) {
        Random rand = new Random();
    }

    @Override
    public void chooseOffer(List<Player> players) {}
}