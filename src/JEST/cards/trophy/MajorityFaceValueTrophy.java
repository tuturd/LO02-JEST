package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityFaceValueTrophy implements Trophy {

    private final int faceValue;

    public MajorityFaceValueTrophy(int faceValue) {
        this.faceValue = faceValue;
    }

    @Override
    public Player getWinner(List<Player> players) {
        Map<Player, Integer> counts = new HashMap<>();

        for (Player player : players) {
            int count = 0;

            for (Card card : player.getJest().getCards()) {
                if (card.getFaceValue() == faceValue) {
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
