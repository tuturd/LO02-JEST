package JEST;

import JEST.cards.*;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

/**
 * This class defines a player, real or virtual.
 */
public class Player implements Serializable {
    private final String firstName;
    private final String lastName;
    protected Jest jest;
    protected Offer currentOffer;

    /**
     * The player is defined by his first name and his last name.
     * @param firstName
     * @param lastName
     */
    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * The REAL player makes his offer : it retrieves 2 OfferCards (a face-up or face down card) and creates the player's offer.
     * @param c1 : this is the face-up card of his offer.
     * @param c2 : this is the face-down card of his offer.
     */
    public void makeOffer(Card c1, Card c2) {
        OfferCard offerCard1 = new OfferCard(c1, true);
        OfferCard offerCard2 = new OfferCard(c2, false);
        this.currentOffer = new Offer(offerCard1, offerCard2);
    }
    
    /**
     * The REAL player is asked to choose the player whose offer they want to receive, and then which card they want to receive (face down or face up).
     * @param players : all the players where we can get one card of their offer.
     */
    public void chooseOffer(List<Player> players) {
    	((Player) players).getCurrentOffer();
    	System.out.println("Choisissez le joueur dont vous voulez récupérer une carte de l'offre");
		int n;
		Scanner sc=new Scanner(System.in);
		n=sc.nextInt();
		System.out.println("Carte montrée ou cachée");
		boolean faceUp;
		faceUp=sc.nextBoolean();
		players.get(n).currentOffer.takeCard(faceUp);
    }
    
    /**
     * The player, virtual or real, draws from the deck selected.
     * @param deck : the deck selected (the general or the restOfCards {@link Deck}).
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

    @Override
    public String toString() { return String.format("%s %s", firstName, lastName); }
}
