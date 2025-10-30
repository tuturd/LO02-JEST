package JEST;

import JEST.cards.Card;
import JEST.cards.Jest;
import JEST.cards.Offer;
import JEST.cards.OfferCard;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Player implements Serializable {
    private final String firstName;
    private final String lastName;
    private Jest jest; //normalement c'est inutile
    private Offer currentOffer;

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void makeOffer(Card c1, Card c2) {
        OfferCard offerCard1 = new OfferCard(c1, true);
        OfferCard offerCard2 = new OfferCard(c2, false);
        this.currentOffer = new Offer(offerCard1, offerCard2);
    }

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

    public void drawCard() {}

    public Jest getJest() {
    	return this.jest;
    }
    
    public Offer getCurrentOffer() {
    	return this.currentOffer; 
    }

    @Override
    public String toString() { return String.format("%s %s", firstName, lastName); }
}
