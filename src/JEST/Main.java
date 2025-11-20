package JEST;

import java.util.Scanner;

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

    public static void main(String[] args) {
        Main main = Main.getInstance();
        Game game = main.getGame();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Voulez-vous commencer une nouvelle partie (1) ou charger une partie existante (2) : ");
            String nom = scanner.nextLine();

            if (nom.equals("1")) {
                game.setup();
                break;
            } else if (nom.equals("2")) {
                game.load();
                break;
            } else {
                System.out.println("Entrée invalide. Veuillez entrer 1 ou 2.");
            }
        }
    }
}
