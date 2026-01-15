package JEST.model;

import JEST.controller.PlayerRouter;
import JEST.model.cards.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a player, real or virtual.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String firstName;
    private final String lastName;
    protected Jest jest;
    protected Offer currentOffer;
    protected InterfaceType preferredInterface;

    /**
     * The player is defined by his first name and his last name.
     * His jest is initially empty.
     *
     * @param firstName first name of the player.
     * @param lastName  last name of the player.
     */
    public Player(String firstName, String lastName) {
        this(firstName, lastName, InterfaceType.CONSOLE);
    }

    /**
     * The player is defined by his first name, last name and preferred interface.
     *
     * @param firstName first name of the player.
     * @param lastName last name of the player.
     * @param preferredInterface the player's preferred interface type
     */
    public Player(String firstName, String lastName, InterfaceType preferredInterface) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jest = new Jest();
        this.preferredInterface = preferredInterface;
    }

    /**
     * Get the player's preferred interface type.
     *
     * @return the preferred interface type
     */
    public InterfaceType getPreferredInterface() {
        return this.preferredInterface;
    }

    /**
     * Set the player's preferred interface type.
     *
     * @param preferredInterface the preferred interface type
     */
    public void setPreferredInterface(InterfaceType preferredInterface) {
        this.preferredInterface = preferredInterface;
    }

    /**
     * The REAL player makes his {@link Offer} : it retrieves 2 {@link OfferCard} (a face-up or face down {@link Card} and creates the player's offer.
     *
     * @param c1 this is the face-up card of his offer.
     * @param c2 this is the face-down card of his offer.
     * @param router the player router to use for interaction
     */
    public void makeOffer(Card c1, Card c2, PlayerRouter router) {
        int upsideCardIndex = router.askMakeOffer(this, this.jest.toString(), c1, c2);

        OfferCard offerCard1 = new OfferCard(c1, upsideCardIndex == 1);
        OfferCard offerCard2 = new OfferCard(c2, upsideCardIndex == 2);
        this.currentOffer = new Offer(offerCard1, offerCard2);
    }

    /**
     * The REAL player is asked to choose the player whose offer they want to receive, and then which card they want to receive (face down or face up).
     *
     * @param players all the players where we can get one card of their offer.
     * @param router the player router to use for interaction
     * @return the player whose player chooses the offer.
     */
    public Player chooseOffer(List<Player> players, PlayerRouter router) {
        List<Player> listOtherOfferPlayers = new ArrayList<>();
        for (Player p : players) {
            if (p.getCurrentOffer().isComplete() && p != this) {
                listOtherOfferPlayers.add(p);
            }
        }
        if (listOtherOfferPlayers.isEmpty()) {
            listOtherOfferPlayers.add(this);
        }

        int[] choice = router.askChooseOffer(this, this.jest.toString(), listOtherOfferPlayers);
        int lineIndex = choice[0];
        boolean faceUp = choice[1] == 1;

        Player selectedPlayer = listOtherOfferPlayers.get(lineIndex);

        Card takenCard = selectedPlayer.getCurrentOffer().takeCard(faceUp);
        this.jest.addCard(takenCard);
        router.displayCardAddedToJest(this, takenCard);
        return selectedPlayer;
    }

    /**
     * The player, virtual or real, draws from the {@link Deck} selected.
     *
     * @param deck the deck selected (the general or the restOfCards).
     */
    public void drawCard(Deck deck) {
        this.currentOffer.addOfferCard(new OfferCard(deck.deal(), !this.currentOffer.getFirstOfferCard().isUpside()));
    }

    public Jest getJest() {
        return this.jest;
    }

    public Offer getCurrentOffer() {
        return this.currentOffer;
    }

    public void setCurrentOffer(Offer currentOffer) {
        this.currentOffer = currentOffer;
    }

    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

    /**
     * Enumeration for player interface types.
     */
    public enum InterfaceType {
        GUI, CONSOLE
    }
}
