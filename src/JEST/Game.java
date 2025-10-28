// src/Game.java
package JEST;

import JEST.cards.Deck;
import JEST.cards.trophy.Trophy;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static Game instance;
    private List<Player> players;
    private Deck deck;
    private List<Trophy> trophies;

    private Game() {
        this.players = new  ArrayList<>();
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

        Scanner scanner = new Scanner(System.in);

        int playerNumber = 0;
        while (playerNumber == 0) {
            System.out.print("Nombre de joueurs : ");
            try {
                String playerNumberString = scanner.nextLine();
                int playerNumberTmp = Integer.parseInt(playerNumberString);
                if ((playerNumberTmp > 2) && (playerNumberTmp < 5)) {
                    playerNumber = playerNumberTmp;
                } else {
                    System.out.println("Le nombre de joueurs doit être de 3 ou 4 ! (3 conseillé)");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre !");
            }
        }

        for (int i = 0; i < playerNumber; i++) {
            System.out.printf("Prénom du joueur %d : ", i+1);
            String firstName = scanner.nextLine();
            System.out.printf("Nom du joueur %d : ", i+1);
            String lastName = scanner.nextLine();
            this.players.add(new Player(firstName, lastName));
            System.out.printf(">>> Joueur n°%d créé : %s %s\n", i+1, firstName, lastName);
        }

        System.out.print("Deck > Initialisation...\n");
        deck.fill();
        System.out.print("Deck > Mélange...\n");
        deck.shuffle();
        System.out.print("Deck > OK\n");
    }

    public void playRound() {
        // phases : deal, offer, take
    }

    public void awardTrophies() {
        // attribuer selon conditions
    }

    public Player determineWinner() {
        // déterminer le gagnant
        return null;
    }

    public Deck getDeck() {
        return deck;
    }

    public void save() {
        System.out.println("Sauvegarde de la partie > En cours...");

        System.out.println("Sauvegarde de la partie > OK");
    }

    public void load() {
        System.out.println("Chargement de la partie > En cours...");
        System.out.println("Chargement de la partie > OK");
    }
}
