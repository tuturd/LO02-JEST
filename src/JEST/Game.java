// src/Game.java
package JEST;

import JEST.cards.Deck;
import JEST.cards.trophy.Trophy;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements Serializable {
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

    private static boolean isValidFilename(String name) {
        if (name == null) return false;
        name = name.trim();
        // \p{L} accepte toutes les lettres Unicode (lettres accentuées incluses)
        return !name.isEmpty() && name.matches("^[A-Za-z0-9]+$");
    }

    public void save() {

        File savesDir = new File("saves");
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }
        
        String filename = null;
        while (filename == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nom de la sauvegarde : ");
            String filenameInput = scanner.nextLine();
            if (isValidFilename(filenameInput)) {
                filename = filenameInput;
            } else {
                System.out.println("Nom de fichier invalide. Veuillez utiliser uniquement des lettres.");
            }
        }

        File outFile = new File(savesDir, filename + ".jest");

        System.out.println("Sauvegarde de la partie > En cours...");
        try (FileOutputStream fos = new FileOutputStream(outFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);

            System.out.println("Sauvegarde de la partie > OK");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Sauvegarde de la partie > ERROR");
        }

    }

    public void load() {
        System.out.println("Chargement de la partie > En cours...");

        File savesDir = new File("saves");
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }

        File[] files = savesDir.listFiles((d, name) -> name.toLowerCase().endsWith(".jest"));
        if (files == null || files.length == 0) {
            System.out.println("Aucune sauvegarde .jest trouvee dans le dossier saves.");
            return;
        }

        System.out.println("Sauvegardes disponibles :");
        for (int i = 0; i < files.length; i++) {
            System.out.printf("%d) %s%n", i + 1, files[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > files.length) {
            System.out.print("Entrez le numero du fichier a charger : ");
            String line = scanner.nextLine();
            try {
                choice = Integer.parseInt(line);
                if (choice < 1 || choice > files.length) {
                    System.out.println("Numero hors champ, reessayez.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entree invalide, tapez un nombre.");
            }
        }

        File selected = files[choice - 1];

        try (FileInputStream fis = new FileInputStream(selected);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Game loadedGame = (Game) ois.readObject();
            instance = loadedGame;
            this.players = loadedGame.players;
            this.deck = loadedGame.deck;
            this.trophies = loadedGame.trophies;

            System.out.println("Chargement de la partie > OK");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Chargement de la partie > ERROR");
        }

    }
}
