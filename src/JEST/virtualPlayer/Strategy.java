package JEST.virtualPlayer;

import JEST.cards.Card;
import JEST.cards.Deck;

import java.util.List;

import JEST.Player;
import JEST.cards.Offer;

/**
 * The interface creates the methods to make or choose the offer.
 */
public interface Strategy {
    public void makeOffer(VirtualPlayer player, Card c1, Card c2);
	public Player chooseOffer(VirtualPlayer player, List<Player> players);
    public String toString();
}