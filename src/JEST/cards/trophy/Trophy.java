package JEST.cards.trophy;

import JEST.Player;

import java.util.List;

public interface Trophy {
    Player getWinner(List<Player> players);
    String getName();
}

