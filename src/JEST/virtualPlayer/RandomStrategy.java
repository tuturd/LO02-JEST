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

public class RandomStrategy implements Strategy, Serializable {
    @Override
    public void makeOffer(VirtualPlayer player, Card c1, Card c2) {
        Card upsideCard = new Random().nextBoolean() ? c1 : c2;
        Card downsideCard = upsideCard == c2 ? c1 : c2;

        player.setCurrentOffer(
                new Offer(
                        new OfferCard(upsideCard, true),
                        new OfferCard(downsideCard, false)
                )
        );
    }

    @Override
    public Player chooseOffer(VirtualPlayer player, List<Player> players) {
        List<Player> listOtherOfferPlayers = new ArrayList<>();
        for (Player p : players) {
            if (p.getCurrentOffer().isComplete()) {
                listOtherOfferPlayers.add(p);
            }
        }
        Random randOffer = new Random();
        Offer offer = listOtherOfferPlayers.get(randOffer.nextInt(listOtherOfferPlayers.size())).getCurrentOffer();
        boolean b = new Random().nextBoolean();

        Card selectedCard = offer.takeCard(b);
        player.getJest().addCard(selectedCard);

        return listOtherOfferPlayers.stream()
                .filter(p -> p.getCurrentOffer().getCards().stream()
                        .anyMatch(oc -> oc.getCard().equals(selectedCard)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Random";
    }
}