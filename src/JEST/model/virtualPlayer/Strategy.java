package JEST.model.virtualPlayer;

import JEST.model.Player;
import JEST.model.cards.Card;

import java.util.List;

/**
 * The interface creates the methods to make or choose the offer.
 */
public interface Strategy {
    /**
     * The virtual player makes an offer according to his strategy.
     *
     * @param player the player who chooses the offer.
     * @param c1     the first card of the offer.
     * @param c2     the second card of the offer.
     */
    public void makeOffer(VirtualPlayer player, Card c1, Card c2);

    /**
     * The virtual player chooses an offer according to his strategy.
     *
     * @param player  the player who choose the offer.
     * @param players the list of the players in the game.
     * @return the player whose offer is chosen by the virtual player.
     */
    public Player chooseOffer(VirtualPlayer player, List<Player> players);

    public String toString();
}