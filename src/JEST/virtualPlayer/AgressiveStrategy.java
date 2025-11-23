package JEST.virtualPlayer;

import JEST.cards.Card;
import JEST.cards.Deck;
import JEST.Player;
import JEST.cards.Offer;
import JEST.cards.OfferCard;
import JEST.cards.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgressiveStrategy extends Player implements Strategy, Serializable {

	public AgressiveStrategy(String firstName, String lastName) {
		super(firstName, lastName);
	}

	public void makeOffer(Card c1, Card c2) {

	}

	@Override
	public void chooseOffer(List<Player> players) {
		List<Offer> listOtherOffers = new ArrayList<>();
		for (Player player : players) {
			listOtherOffers.add(player.getCurrentOffer());
		}
		boolean testJestJoker = this.jest.getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);
		boolean testJestHeart = this.jest.getCards().stream().anyMatch(card -> card.getSuit() == Suit.HEART);
		if (testJestJoker) { // if the player has a Joker in his Jest
			if (testJestHeart) { // and if he has at least one Heart in his Jest
				for (Offer offer : listOtherOffers) {
					for (OfferCard oc : offer.getCards()) { // if there is a Heart in the face up cards of the offers of
															// the players, he adds the card in his Jest
						if (oc.isUpside() && oc.getCard().getSuit() == Suit.HEART) {
							this.jest.addCard(oc.getCard()); // remove the face up HEART card from the deck and add it to his
																// jest
							break;
						} else if (oc.isUpside() && oc.getCard().getSuit() == Suit.SPADE) {
							this.jest.addCard(oc.getCard()); // remove the face up SPADE card from the deck and add it to his
																// jest
							break;
						} else if (oc.isUpside() && oc.getCard().getSuit() == Suit.CLUB) {
							this.jest.addCard(oc.getCard()); // remove the face up CLUB card from the deck and add it to his
																// jest
							break;
						}
					}
				}
			}
		}
	}
}