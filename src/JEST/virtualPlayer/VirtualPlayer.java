package JEST.virtualPlayer;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.cards.Offer;

import java.io.Serializable;
import java.util.List;

/**
 * A virtual player will make an {@link Offer} and choose an offer under his {@link Strategy} pre-defined.
 */
public class VirtualPlayer extends Player implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strategy strategy;

    /**
     * A virtual player is defined by his first name, his last name, and his strategy.
     * @param firstName first name of the virtual player.
     * @param lastName last name of the virtual player.
     * @param strategy strategy pre-defined.
     */
	public VirtualPlayer(String firstName, String lastName, Strategy strategy) {
        super(firstName, lastName);
        this.strategy = strategy;
    }

	/**
	 * The virtual player makes an offer under his strategy.
	 * @param c1 the first card of the offer.
	 * @param c2 the second card of the offer.
	 */
    public void makeOffer(Card c1, Card c2) {
        System.out.printf("%s a fait une offre via une stratégie %s.\n", this, this.strategy);
        this.strategy.makeOffer(this, c1, c2);
    }

    /**
     * The virtual player chooses an offer under his strategy.
     * @param players the list of all players.
     * @return the player whose virtual player chooses the offer.
     */
    public Player chooseOffer(List<Player> players) {
        System.out.printf("\n%s a choisi une offre via une stratégie %s.\n", this, this.strategy);
        return this.strategy.chooseOffer(this, players);
    }
}
