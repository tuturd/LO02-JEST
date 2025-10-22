package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;
import JEST.cards.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityFaceValueTrophy implements Trophy {
    private final int faceValue;

    public MajorityFaceValueTrophy(int faceValue) {
        this.faceValue = faceValue;
    }

    public Player getWinner(List<Jest> jests) {
        Map<Player, Integer> counts = new HashMap<>();
        for (Jest jest : jests) {
            int count = 0;
            for (Card card : jest.getCards()) {
                if (card.getFaceValue() == faceValue) {
                    count++;
                }
            }
            counts.put(jest.getOwner(), counts.getOrDefault(jest.getOwner(), 0) + count);
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

    public String getName() {
        return "Majority of face value " + faceValue + " Trophy";
    }
}
