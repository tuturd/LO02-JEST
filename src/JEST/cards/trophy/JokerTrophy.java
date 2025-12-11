package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;
import JEST.cards.JokerCard;

import java.io.Serializable;
import java.util.List;

public class JokerTrophy implements Trophy, Serializable {

    @Override
    public Player getWinner(List<Player> players) {
        for (Player player : players) {
            for (Card card : player.getJest().getCards()) {
                if (card instanceof JokerCard) {
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "Joker Trophy";
    }
}
