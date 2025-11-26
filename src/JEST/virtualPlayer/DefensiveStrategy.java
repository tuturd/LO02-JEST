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
    public void makeOffer(Player player, Card c1, Card c2) {
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

    @Override
    public void chooseOffer(Player player, List<Player> players) {
        List<Offer> listOtherOffers = new ArrayList<>();
        long heartCount = player.getJest().getCards().stream().filter(c -> c.getSuit() == Suit.HEART).count();
        for (Player p : players) {
            listOtherOffers.add(p.getCurrentOffer());
        }
        player.getJest().addCard(this.getSafestCard(listOtherOffers, heartCount));
    }

    private int computeRiskOnMakingOffer(Card c) {
        return switch (c.getSuit()) {
            case SPADE, CLUB -> c.getFaceValue();

            case DIAMOND -> -c.getFaceValue();

            case HEART -> 1;

            case JOKER -> 5;
        };
    }

    private int computeRiskOnTakingCard(Card c, long heartCount) {
        switch (c.getSuit()) {
            case CLUB:
                return c.getFaceValue();

            case DIAMOND:
                return -c.getFaceValue();

            case HEART:
                if (heartCount == 0) return 0;
                if (heartCount < 4) return c.getFaceValue();
                return -c.getFaceValue();

            case JOKER:
                if (heartCount == 0) return 2;
                if (heartCount < 4) return 10;
                return -5;

            default:
                return 0;
        }
    }

    private Card getSafestCard(List<Offer> options, long heartCount) {
        Offer safest = null;
        int minRisk = Integer.MAX_VALUE;

        for (Offer offer : options) {
            Card c = offer.getCard(true);
            int risk = this.computeRiskOnTakingCard(c, heartCount);
            if (risk < minRisk) {
                minRisk = risk;
                safest = offer;
            }
        }
        assert safest != null;
        return safest.takeCard(true);
    }
}