// src/Game.java
package JEST; //bonjour

import JEST.cards.Deck;
import JEST.cards.trophy.Trophy;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Game {
    private static Game instance;
    private List<Player> players;
    private Deck deck;
    private List<Trophy> trophies;

    private Game() {
        this.deck = Deck.getInstance();
        this.trophies = new ArrayList<>();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void setup() {
        deck.shuffle();
        // distribuer trophées, etc.
    }

    public void playRound() {
        // phases : deal, offer, take
    }

    public void awardTrophies() {
        // attribuer selon conditions
    }

    public Player determineWinner() {
        return players.stream()
                .max(Comparator.comparingInt(p -> p.getJest().computeScore()))
                .orElse(null);
    }

    public Deck getDeck() {
        return deck;
    }

    private void save() {}
    private void load() {}
}
