// src/Main.java
package JEST;

public class Main {
    private static Main instance;
    private Game game;

    private Main() {
        this.game = Game.getInstance();
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public Game getGame() {
        return game;
    }
}
