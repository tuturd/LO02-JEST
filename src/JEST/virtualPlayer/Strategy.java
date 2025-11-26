package JEST.virtualPlayer;

import JEST.cards.Card;
import JEST.cards.Deck;

import java.util.List;

import JEST.Player;
import JEST.cards.Offer;

public interface Strategy {
    void makeOffer(Player player, Card c1, Card c2);
    void chooseOffer(Player player, List<Player> players);
}