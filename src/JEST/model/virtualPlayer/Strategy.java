package JEST.model.virtualPlayer;

import JEST.model.cards.Card;

import java.util.List;

import JEST.model.Player;

/**
 * The interface creates the methods to make or choose the offer.
 */
public interface Strategy {
    public void makeOffer(VirtualPlayer player, Card c1, Card c2);
	public Player chooseOffer(VirtualPlayer player, List<Player> players);
    public String toString();
}