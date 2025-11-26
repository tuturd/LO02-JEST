package JEST.virtualPlayer;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.Offer;
import JEST.cards.OfferCard;
import JEST.cards.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefensiveStrategy implements Strategy, Serializable {
    @Override
    public void makeOffer(VirtualPlayer player, Card c1, Card c2) {
        int riskCard1 = this.computeRiskOnMakingOffer(c1);
        int riskCard2 = this.computeRiskOnMakingOffer(c2);

        Card upsideCard;
        Card downsideCard;

        if (riskCard1 <= riskCard2) {
            upsideCard = c1;
            downsideCard = c2;
        } else {
            upsideCard = c2;
            downsideCard = c1;
        }

        player.setCurrentOffer(
                new Offer(
                        new OfferCard(upsideCard, true),
                        new OfferCard(downsideCard, false)
                )
        );
    }
    
    private int computeRiskOnMakingOffer(Card c) {
        return switch (c.getSuit()) {
            case SPADE, CLUB -> c.getFaceValue();

            case DIAMOND -> -c.getFaceValue();

            case HEART -> 1;

            case JOKER -> 5;
        };
    }

    
    @Override
    public void chooseOffer(VirtualPlayer player, List<Player> players) {
        List<Offer> listOtherOffers = new ArrayList<>();
        long heartCount = player.getJest().getCards().stream().filter(c -> c.getSuit() == Suit.HEART).count();
        boolean testJestJoker = player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);
        for (Player p : players) {
        	  if (p.getCurrentOffer().isComplete()) {
        	    listOtherOffers.add(p.getCurrentOffer());
        	  }
        }
        player.getJest().addCard(this.getSafestCard(listOtherOffers, heartCount, testJestJoker));
    }

    private int computeRiskOnTakingCard(Card c, long heartCount, boolean testJestJoker) { //the risk increases with the value returned
        switch (c.getSuit()) {
            case CLUB, SPADE:
                return -c.getFaceValue();

            case DIAMOND:
                return c.getFaceValue();

            case HEART:
            	if (testJestJoker && heartCount == 3) return -10;
                if (!testJestJoker) return 0;
                if (testJestJoker && heartCount < 3) return c.getFaceValue();
                return -c.getFaceValue();

            case JOKER:
                if (heartCount == 0) return -5;
                if (heartCount > 0) return 10;

            default:
                return 0;
        }
    }

    private Card getSafestCard(List<Offer> options, long heartCount, boolean testJestJoker) {
        Offer safest = null;
        int minRisk = Integer.MAX_VALUE;

        for (Offer offer : options) {
            Card c = offer.getCard(true);
            int risk = this.computeRiskOnTakingCard(c, heartCount, testJestJoker);
            if (risk < minRisk) {
                minRisk = risk;
                safest = offer;
            }
        }
        assert safest != null;
        return safest.takeCard(true);
    }
}