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
    public void makeOffer(Player player, Card c1, Card c2) {
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
    public void chooseOffer(Player player, List<Player> players) {
        List<Offer> listOtherOffers = new ArrayList<>();
        for (Player p : players) {
            listOtherOffers.add(p.getCurrentOffer());
        }
        Random randOffer = new Random();
        Offer offer = listOtherOffers.get(randOffer.nextInt(listOtherOffers.size()));
        boolean b = new Random().nextBoolean();
        player.getJest().addCard(offer.takeCard(b));
    }
}