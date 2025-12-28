package JEST.model;

import JEST.model.cards.*;

import java.io.Serializable;
import java.util.ArrayList;
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
    private static final long serialVersionUID = 1L;

    /**
     * The player is defined by his first name and his last name.
     * His jest is initially empty.
     * @param firstName first name of the player.
     * @param lastName last name of the player.
     */
    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jest = new Jest();
    }

    /**
     * The REAL player makes his {@link Offer} : it retrieves 2 {@link OfferCard} (a face-up or face down {@link Card} and creates the player's offer.
     * @param c1 : this is the face-up card of his offer.
     * @param c2 : this is the face-down card of his offer.
     */
    public void makeOffer(Card c1, Card c2) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.printf("%s %s, voici ce que contient votre Jest : %s", this.firstName, this.lastName, this.jest);
        System.out.println("\nC'est votre tour de faire une offre.\n----------\n");
        System.out.println("Vos cartes à disposition: ");
        System.out.println("1: " + c1);
        System.out.println("2: " + c2);
        System.out.println("Quelle carte souhaitez-vous laisser visible ? ");
        String upsideCardIndexString = scanner.nextLine();
        int upsideCardIndex = Integer.parseInt(upsideCardIndexString);

        OfferCard offerCard1 = new OfferCard(c1, upsideCardIndex == 1);
        OfferCard offerCard2 = new OfferCard(c2, upsideCardIndex == 2);
        this.currentOffer = new Offer(offerCard1, offerCard2);
    }

    /**
     * The REAL player is asked to choose the player whose offer they want to receive, and then which card they want to receive (face down or face up).
     * @param players all the players where we can get one card of their offer.
     * @return the player whose player chooses the offer.
     */
    public Player chooseOffer(List<Player> players) {
        Scanner scanner = new Scanner(System.in);

        List<Player> listOtherOfferPlayers = new ArrayList<>();
        for (Player p : players) {
            if (p.getCurrentOffer().isComplete() && p != this) {
                listOtherOfferPlayers.add(p);
            }
        }
        if (listOtherOfferPlayers.isEmpty()) {
            listOtherOfferPlayers.add(this);
        }
        
        System.out.printf("%s %s, voici ce que contient votre Jest : %s", this.firstName, this.lastName, this.jest);
        System.out.print("\nC'est votre tour de choisir une offre.\n----------\n");
        System.out.println("Les cartes à disposition sont:");
        int i = 0;
        for (Player player : listOtherOfferPlayers) {
        	System.out.printf("%d: %s possède %s et une carte cachée\n", i, player, player.getCurrentOffer().getCard(true));
            i++;
        }

        System.out.println("Choisissez la ligne dont vous voulez récupérer une carte de l'offre: ");
        int lineIndex = scanner.nextInt();
        System.out.println("Carte montrée (1) ou cachée (2): ");
        boolean faceUp = scanner.nextInt() == 1;

        Player selectedPlayer = listOtherOfferPlayers.get(lineIndex);

        this.jest.addCard(selectedPlayer.getCurrentOffer().takeCard(faceUp));
        System.out.printf("%s %s, vous avez ajouté à votre Jest %s. \n----------\n", this.firstName, this.lastName, this.jest.getCards().get(this.jest.getCards().size() - 1));
        return selectedPlayer;
    }

    /**
     * The player, virtual or real, draws from the {@link Deck} selected.
     * @param deck : the deck selected (the general or the restOfCards).
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
}
