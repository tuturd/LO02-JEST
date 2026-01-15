package JEST.model.virtualPlayer;

import JEST.model.Player;
import JEST.model.cards.Card;
import JEST.model.cards.Offer;
import JEST.model.cards.OfferCard;
import JEST.model.cards.Suit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A virtual player with the defensive strategy takes as few risks as possible.
 */
public class DefensiveStrategy implements Strategy, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * We compute the risk of each card.
     * Then, we compare the points computed of each card.
     * The card with the most points is upside (because it is the riskiest card), and the other is downside.
     */
    public void makeOffer(VirtualPlayer player, Card c1, Card c2) {
        int riskCard1 = this.computeRiskOnMakingOffer(player, c1);
        int riskCard2 = this.computeRiskOnMakingOffer(player, c2);

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

    /**
     * We compute the risk of each card.
     * The card with the fewest points is chosen by the virtual player.
     *
     * @param player the player who chooses the card.
     * @param c      the card to evaluate.
     * @return the points of the card.
     */
    private int computeRiskOnMakingOffer(VirtualPlayer player, Card c) {
        long heartCount = player.getJest().getCards().stream().filter(card -> card.getSuit() == Suit.HEART).count();
        boolean testJestJoker = player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);
        switch (c.getSuit()) {
            case SPADE, CLUB:
                return c.getFaceValue();

            case DIAMOND:
                return -c.getFaceValue();

            case HEART:
                if (!testJestJoker) return 0;
                if (heartCount == 3) return 10;
                if (heartCount < 3) return -c.getFaceValue();

            case JOKER:
                if (heartCount == 0 || heartCount == 4) return 5;
                return -10;

            default:
                return 0;
        }
    }

    /**
     * We compute the points of each face-up cards in the other offers.
     * The virtual player chooses the safest card among the face-up cards.
     *
     * @param player  the player who choose the offer.
     * @param players the list of the players of the game.
     * @return the player whose virtual player chooses the offer.
     */
    public Player chooseOffer(VirtualPlayer player, List<Player> players) {
        List<Player> listOtherOfferPlayers = new ArrayList<>();
        long heartCount = player.getJest().getCards().stream().filter(c -> c.getSuit() == Suit.HEART).count();
        boolean testJestJoker = player.getJest().getCards().stream().anyMatch(card -> card.getSuit() == Suit.JOKER);
        for (Player p : players) {
            if (p.getCurrentOffer().isComplete()) {
                listOtherOfferPlayers.add(p);
            }
        }
        Card safestCard = this.getSafestCard(listOtherOfferPlayers.stream().map(Player::getCurrentOffer).toList(), heartCount, testJestJoker);
        player.getJest().addCard(safestCard);

        return listOtherOfferPlayers.stream()
                .filter(p -> p.getCurrentOffer().getCards().stream()
                        .anyMatch(oc -> oc.getCard().equals(safestCard)))
                .findFirst()
                .orElse(null);
    }

    /**
     * We compute the risk of each card.
     * The card with the fewest points is chosen by the virtual player.
     *
     * @param player the player who chooses the card.
     * @param c      the card to evaluate.
     * @return the points of the card.
     */
    private int computeRiskOnTakingCard(Card c, long heartCount, boolean testJestJoker) { //the risk increases with the value returned
        switch (c.getSuit()) {
            case CLUB, SPADE:
                return -c.getFaceValue();

            case DIAMOND:
                return c.getFaceValue();

            case HEART:
                if (!testJestJoker) return 0;
                if (heartCount == 3) return -10;
                if (heartCount < 3) return c.getFaceValue();

            case JOKER:
                if (heartCount == 0 || heartCount == 4) return -5;
                return 10;

            default:
                return 0;
        }
    }

    /**
     * We compute the points of each face-up cards in the other offers.
     * The virtual player chooses the safest card among the face-up cards.
     *
     * @param options    the list of the offers of the game.
     * @param heartCount the number of hearts in the virtual player's jest.
     * @param testJestJoker whether the virtual player's jest contains a joker.
     * @return the card chosen by the virtual player.
     */
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

    public String toString() {
        return "Defensive";
    }
}