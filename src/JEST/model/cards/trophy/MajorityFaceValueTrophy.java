package JEST.model.cards.trophy;

import JEST.model.Player;
import JEST.model.cards.Card;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The winner is the {@link Player} with most cards of this face value.
 */
public class MajorityFaceValueTrophy implements Trophy, Serializable {
    private static final long serialVersionUID = 1L;

    private final int faceValue;

    /**
     * We define the face value associated to the {@link Trophy}.
     *
     * @param faceValue face value associated to the trophy.
     */
    public MajorityFaceValueTrophy(int faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * We add one to the the counter if the card has the same face value as the trophy.
     *
     * @param players all the players of the game.
     * @return the winner of the trophy.
     */
    public Player getWinner(List<Player> players) {
        Map<Player, Integer> counts = new HashMap<>();

        for (Player player : players) {
            int count = 0;

            for (Card card : player.getJest().getCards()) {
                if (card.getFaceValue() == this.faceValue) {
                    count++;
                }
            }

            counts.put(player, count);
        }

        Player winner = null;
        int maxCount = 0;

        for (Map.Entry<Player, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                winner = entry.getKey();
            }
        }

        return winner;
    }

    @Override
    public String getName() {
        return "Majority of face value " + faceValue + " Trophy";
    }
}
