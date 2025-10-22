package JEST.cards.trophy;

import JEST.Player;
import JEST.cards.Jest;

import java.util.List;

public interface Trophy {
    Player getWinner(List<Jest> jests);
    String getName();
}

