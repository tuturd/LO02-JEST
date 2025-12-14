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

public class AgressiveStrategy implements Strategy, Serializable {

    public void makeOffer(VirtualPlayer player, Card c1, Card c2) {
        int bestCard1 = this.computeAdvantageOnMakingOffer(player, c1);
        int bestCard2 = this.computeAdvantageOnMakingOffer(player, c2);

        Card upsideCard;
        Card downsideCard;

        if (bestCard1 >= bestCard2) {
            upsideCard = c1;
            downsideCard = c2;
        } else {
            upsideCard = c2;
            downsideCard = c1;
        }

        player.setCurrentOffer(new Offer(new OfferCard(upsideCard, true), new OfferCard(downsideCard, false)));
    }

    private int computeAdvantageOnMakingOffer(VirtualPlayer player, Card c) {
        long heartCount = player.getJest().getCards().stream().filter(card -> card.getSuit() == Suit.HEART).count();
        boolean testJestJoker = player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);

        switch (c.getSuit()) {
            case CLUB:
                if (c.getFaceValue() == 1 && !isAnyCardInSuit(player, Suit.CLUB)) return -5;
                if (player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.SPADE && card.getFaceValue() == c.getFaceValue()))
                    return -2;
                return -c.getFaceValue();

            case SPADE:
                if (c.getFaceValue() == 1 && !isAnyCardInSuit(player, Suit.SPADE)) return -5;
                if (player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.CLUB && card.getFaceValue() == c.getFaceValue()))
                    return -2;
                return -c.getFaceValue();

            case DIAMOND:
                if (c.getFaceValue() == 1 && !isAnyCardInSuit(player, Suit.DIAMOND)) return 5;
                return c.getFaceValue();

            case HEART:
                if (testJestJoker && heartCount == 3) return -10;
                if (c.getFaceValue() == 1 && !isAnyCardInSuit(player, Suit.HEART)) return -5;
                if (!testJestJoker) return 0;
                if (heartCount < 3) return 10;

            case JOKER:
                if (heartCount == 4) return -10;
                if (heartCount == 0) return -4;
                return -10;

            default:
                return 0;
        }
    }

    @Override
    public Player chooseOffer(VirtualPlayer player, List<Player> players) {
        List<Player> listOtherOfferPlayers = new ArrayList<>();
        for (Player p : players) {
            if (p.getCurrentOffer().isComplete()) {
                listOtherOfferPlayers.add(p);
            }
        }
        Card bestCard = this.getBestCard(player, listOtherOfferPlayers.stream().map(Player::getCurrentOffer).toList());
        player.getJest().addCard(bestCard);

        return listOtherOfferPlayers.stream()
                .filter(p -> p.getCurrentOffer().getCards().stream()
                        .anyMatch(oc -> oc.getCard().equals(bestCard)))
                .findFirst()
                .orElse(null);
    }

    private boolean isAnyCardInSuit(VirtualPlayer player, Suit suit) {
        return player.getJest().getCards().stream().anyMatch(c -> c.getSuit() == suit);
    }

    private int computeAdvantageOnTakingCard(VirtualPlayer player, Card c) {
        long heartCount = player.getJest().getCards().stream().filter(card -> card.getSuit() == Suit.HEART).count();
        boolean testJestJoker = player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);

        switch (c.getSuit()) {
            case CLUB:
                if (c.getFaceValue() == 1 && !this.isAnyCardInSuit(player, Suit.CLUB)) return 5;
                if (player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.SPADE && card.getFaceValue() == c.getFaceValue()))
                    return 2;
                return c.getFaceValue();

            case SPADE:
                if (c.getFaceValue() == 1 && !this.isAnyCardInSuit(player, Suit.SPADE)) return 5;
                if (player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.CLUB && card.getFaceValue() == c.getFaceValue()))
                    return 2;
                return c.getFaceValue();

            case DIAMOND:
                if (c.getFaceValue() == 1 && !this.isAnyCardInSuit(player, Suit.DIAMOND)) return -5;
                return -c.getFaceValue();

            case HEART:
                if (testJestJoker && heartCount == 3) return 10;
                if (c.getFaceValue() == 1 && !this.isAnyCardInSuit(player, Suit.HEART)) return 5;
                if (!testJestJoker) return 0;
                if (heartCount < 3) return -10;

            case JOKER:
                if (heartCount == 4) return 10;
                if (heartCount == 0) return 4;
                return 10;

            default:
                return 0;
        }
    }

    private Card getBestCard(VirtualPlayer player, List<Offer> options) {
        Offer best = null;
        int maxPoints = Integer.MIN_VALUE;

        for (Offer offer : options) {
            Card c = offer.getCard(true);
            int points = this.computeAdvantageOnTakingCard(player, c);
            if (maxPoints < points) {
                maxPoints = points;
                best = offer;
            }
        }

        if (maxPoints <= 0) {
            Random randOffer = new Random();
            return options.get(randOffer.nextInt(options.size())).takeCard(false);
        } else {
            assert best != null;
            return best.takeCard(true);
        }
    }

    @Override
    public String toString() {
        return "Agressive";
    }
}