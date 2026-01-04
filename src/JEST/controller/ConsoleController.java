package JEST.controller;

import JEST.model.Player;
import JEST.model.cards.Card;

import java.util.List;
import java.util.Scanner;

/**
 * Console implementation of the GameInterface.
 * All interactions happen through the console using Scanner.
 */
public class ConsoleController implements GameController {

    private final Scanner scanner;

    /**
     * Constructor for the console controller.
     * Initializes the Scanner for reading console input.
     */
    public ConsoleController() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Ask the user to start a new game or load an existing one via console input.
     *
     * @return 1 for new game, 2 for load game
     */
    @Override
    public int askNewOrLoadGame() {
        while (true) {
            System.out.print("Voulez-vous commencer une nouvelle partie (1) ou charger une partie existante (2) : ");
            String input = scanner.nextLine();
            System.out.println();

            if (input.equals("1")) {
                return 1;
            } else if (input.equals("2")) {
                return 2;
            } else {
                System.out.println("Entrée invalide. Veuillez entrer 1 ou 2.");
            }
        }
    }

    /**
     * Ask for the number of players via console input.
     * The number must be 3 or 4.
     *
     * @return the number of players (3 or 4)
     */
    @Override
    public int askNumberOfPlayers() {
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
        return playerNumber;
    }

    /**
     * Ask if a player is virtual via console input.
     *
     * @param playerNumber the player number
     * @return true if virtual (user enters "o"), false otherwise
     */
    @Override
    public boolean askIsVirtualPlayer(int playerNumber) {
        System.out.printf("Le joueur %d est-il un joueur virtuel ? (o/n) : ", playerNumber);
        String isVirtual = scanner.nextLine().trim().toLowerCase();
        return isVirtual.equals("o");
    }

    /**
     * Ask for the first name of a player via console input.
     *
     * @param playerNumber the player number
     * @return the first name entered by the user
     */
    @Override
    public String askFirstName(int playerNumber) {
        System.out.printf("Prénom du joueur %d : ", playerNumber);
        return scanner.nextLine();
    }

    /**
     * Ask for the last name of a player via console input.
     *
     * @param playerNumber the player number
     * @return the last name entered by the user
     */
    @Override
    public String askLastName(int playerNumber) {
        System.out.printf("Nom du joueur %d : ", playerNumber);
        return scanner.nextLine();
    }

    /**
     * Ask for the strategy of a virtual player via console input.
     *
     * @param playerNumber the player number
     * @return "1" for random, "2" for defensive, "3" for aggressive
     */
    @Override
    public String askStrategy(int playerNumber) {
        System.out.printf("Quelle stratégie pour le joueur virtuel %d ? (1: Aléatoire, 2: Défensive, 3: Agressive) : ", playerNumber);
        String strategyChoice = scanner.nextLine().trim();
        if (!strategyChoice.equals("1") && !strategyChoice.equals("2") && !strategyChoice.equals("3")) {
            System.out.println("Choix invalide, stratégie aléatoire par défaut.");
            return "1";
        }
        return strategyChoice;
    }

    /**
     * Display a message about player creation to the console.
     *
     * @param playerNumber the player number
     * @param firstName    the first name
     * @param lastName     the last name
     * @param isVirtual    true if virtual player
     */
    @Override
    public void displayPlayerCreated(int playerNumber, String firstName, String lastName, boolean isVirtual) {
        if (isVirtual) {
            System.out.printf(">>> Joueur virtuel n°%d créé\n", playerNumber);
        } else {
            System.out.printf(">>> Joueur n°%d créé : %s %s\n", playerNumber, firstName, lastName);
        }
    }

    /**
     * Display the trophy cards to the console.
     *
     * @param trophyCards the trophy cards to display
     */
    @Override
    public void displayTrophyCards(List<Card> trophyCards) {
        if (trophyCards.size() == 1) {
            System.out.println("Trophies > Prendre la première...");
            System.out.println("Le trophée est : " + trophyCards + ".");
        } else {
            System.out.println("Trophies > Prendre les 2 premières...");
            System.out.println("Les trophées sont : " + trophyCards + ".");
        }
    }

    /**
     * Ask a player to make an offer via console input.
     * The player chooses which card to show face up.
     *
     * @param player the player making the offer
     * @param jest   the player's current jest
     * @param c1     first card
     * @param c2     second card
     * @return the index of the card to show face up (1 or 2)
     */
    @Override
    public int askMakeOffer(Player player, String jest, Card c1, Card c2) {
        System.out.printf("%s, voici ce que contient votre Jest : %s", player, jest);
        System.out.println("\nC'est votre tour de faire une offre.\n----------\n");
        System.out.println("Vos cartes à disposition: ");
        System.out.println("1: " + c1);
        System.out.println("2: " + c2);
        System.out.println("Quelle carte souhaitez-vous laisser visible ? ");
        String upsideCardIndexString = scanner.nextLine();
        return Integer.parseInt(upsideCardIndexString);
    }

    /**
     * Ask a player to choose an offer via console input.
     * The player selects which player's offer to take and which card (face up or face down).
     *
     * @param player           the player choosing
     * @param jest             the player's current jest
     * @param availablePlayers the list of players with available offers
     * @return an array with [playerIndex, cardChoice] where cardChoice is 1 for face-up, 2 for face-down
     */
    @Override
    public int[] askChooseOffer(Player player, String jest, List<Player> availablePlayers) {
        System.out.printf("%s, voici ce que contient votre Jest : %s", player, jest);
        System.out.print("\nC'est votre tour de choisir une offre.\n----------\n");
        System.out.println("Les cartes à disposition sont:");
        int i = 0;
        for (Player p : availablePlayers) {
            System.out.printf("%d: %s possède %s et une carte cachée\n", i, p, p.getCurrentOffer().getCard(true));
            i++;
        }

        System.out.println("Choisissez la ligne dont vous voulez récupérer une carte de l'offre: ");
        int lineIndex = scanner.nextInt();
        System.out.println("Carte montrée (1) ou cachée (2): ");
        int cardChoice = scanner.nextInt();
        scanner.nextLine();

        return new int[]{lineIndex, cardChoice};
    }

    /**
     * Display the card added to a player's jest to the console.
     *
     * @param player the player who received the card
     * @param card   the card that was added
     */
    @Override
    public void displayCardAddedToJest(Player player, Card card) {
        System.out.printf("%s, vous avez ajouté à votre Jest %s. \n----------\n", player, card);
    }

    /**
     * Display the final ranking and winner to the console.
     *
     * @param ranking the ranking as a string
     */
    @Override
    public void displayWinner(String ranking) {
        System.out.println(ranking);
    }

    /**
     * Ask if the user wants to save the game via console input.
     *
     * @return true if the user wants to save (enters "o"), false otherwise
     */
    @Override
    public boolean askSaveGame() {
        System.out.print("Voulez-vous sauvegarder la partie ? (o/n) : ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("o");
    }

    /**
     * Ask for the save file name via console input.
     * The file name must contain only letters and numbers.
     *
     * @return the file name without extension
     */
    @Override
    public String askSaveFileName() {
        while (true) {
            System.out.print("Entrez le nom de la sauvegarde (sans extension) : ");
            String fileName = scanner.nextLine().trim();
            if (isValidFilename(fileName)) {
                return fileName;
            } else {
                System.out.println("Nom de fichier invalide. Utilisez uniquement des lettres et des chiffres.");
            }
        }
    }

    /**
     * Display a message to the console.
     *
     * @param message the message to display
     * @param type    the type of message (not used in console implementation)
     */
    @Override
    public void displayMessage(String message, MessageType type) {
        System.out.println(message);
    }

    /**
     * Ask the user to select a file to load via console input.
     * Displays a numbered list of available save files.
     *
     * @param fileNames the list of available save files
     * @return the index of the selected file
     */
    @Override
    public int askSelectFileToLoad(List<String> fileNames) {
        System.out.println("Sauvegardes disponibles :");
        for (int i = 0; i < fileNames.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, fileNames.get(i));
        }

        int choice = -1;
        while (choice < 1 || choice > fileNames.size()) {
            System.out.print("Entrez le numéro du fichier à charger : ");
            String line = scanner.nextLine();
            try {
                choice = Integer.parseInt(line);
                if (choice < 1 || choice > fileNames.size()) {
                    System.out.println("Numéro hors champ, réessayez.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, tapez un nombre.");
            }
        }

        return choice - 1;
    }

    /**
     * Verify if the file name is valid.
     * A valid file name contains only letters and numbers.
     *
     * @param name the file name to check
     * @return true if the file name is valid, false otherwise
     */
    private boolean isValidFilename(String name) {
        if (name == null) return false;
        name = name.trim();
        return !name.isEmpty() && name.matches("^[A-Za-z0-9]+$");
    }
}
