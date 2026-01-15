package JEST.model.virtualPlayer;

import JEST.model.Player;
import JEST.model.cards.Card;
import JEST.model.cards.Offer;
import JEST.model.cards.OfferCard;
import JEST.model.cards.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A virtual player with the aggressive strategy takes all risks to have as many points as possible.
 */
public class AgressiveStrategy implements Strategy, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * We compute the advantage of each card.
     * Then, we compare the points computed of each card.
     * The card with the fewest points is downside (because it is the best card for the player, compared to the other), and the other is upside.
     *
     * @param player player the player who chooses the offer.
     * @param c1 the first card of the offer.
     * @param c2 the second card of the offer.
     */
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

    /**
     * We compute the advantage of each card.
     * The card with the most points is chosen by the virtual player.
     *
     * @param player the player who chooses the card.
     * @param c      the card to evaluate.
     * @return the points of the card.
     */
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
                return 10;

            default:
                return 0;
        }
    }

    /**
     * We compute the points of each face-up cards in the other offers.
     * By default, the virtual player chooses the best card (with most points).
     * But, if the best card in the face-up cards has negative points, he chooses randomly a face-down card.
     *
     * @param player  the player who choose the offer.
     * @param players the list of the players of the game.
     * @return the player whose virtual player chooses the offer.
     */
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

    /**
     * Check if the player has at least one card in the given suit.
     *
     * @param player the player to check.
     * @param suit   the suit to check.
     * @return true if the player has at least one card in the given suit, false otherwise.
     */
    private boolean isAnyCardInSuit(VirtualPlayer player, Suit suit) {
        return player.getJest().getCards().stream().anyMatch(c -> c.getSuit() == suit);
    }

    /**
     * We compute the advantage of each card.
     * The card with the most points is chosen by the virtual player.
     *
     * @param player the player who chooses the card.
     * @param c      the card to evaluate.
     * @return the points of the card.
     */
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
                return -10;

            default:
                return 0;
        }
    }

    /**
     * We compute the points of each face-up cards in the other offers.
     * By default, the virtual player chooses the best card (with most points).
     * But, if the best card in the face-up cards has negative points, he chooses randomly a face-down card.
     *
     * @param player  the player who choose the offer.
     * @param options the list of the offers of the game.
     * @return the card chosen by the virtual player.
     */
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

    public String toString() {
        return "Agressive";
    }
}