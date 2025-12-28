package JEST.model;

import JEST.model.cards.Card;
import JEST.model.cards.Deck;
import JEST.model.cards.DeckType;
import JEST.model.virtualPlayer.AgressiveStrategy;
import JEST.model.virtualPlayer.DefensiveStrategy;
import JEST.model.virtualPlayer.RandomStrategy;
import JEST.model.virtualPlayer.VirtualPlayer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The class starts a game, load or save one, play a round, and determines the winner.
 */
public class Game implements Serializable {
    private static Game instance;
    private List<Player> players;
    private Deck generalDeck;
    private Deck restOfCards;
    private List<Card> trophyCards;
    private static final long serialVersionUID = 1L;

    private Game() {
        this.players = new ArrayList<>();
        this.generalDeck = Deck.getInstance(DeckType.GENERAL);
        this.restOfCards = Deck.getInstance(DeckType.REST_OF_CARDS);
        this.trophyCards = new ArrayList<>();
    }

    /**
     * We use the Singleton design pattern, to guarantee that there is only one instance of this class.
     * @return the only instance of the class.
     */
    public static synchronized Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Destroy the only instance of Game, in order to start a new Game.
     */
    public static void destroyInstance() {
        instance = null;
    }

    /**
     * Verify if the file's name is valid.
     * @param "name" is the file's name.
     * @return true if the file's name is valid, or false if it is invalid.
     */
    private static boolean isValidFilename(String name) {
        if (name == null) return false;
        name = name.trim();
        // \p{L} accepte toutes les lettres Unicode (lettres accentuées incluses)
        return !name.isEmpty() && name.matches("^[A-Za-z0-9]+$");
    }

    /**
     * Load the game.
     */
    public static Game load() {

        File savesDir = new File("saves");
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }

        File[] files = savesDir.listFiles((d, name) -> name.toLowerCase().endsWith(".jest"));
        if (files == null || files.length == 0) {
            System.out.println("Aucune sauvegarde .jest trouvee dans le dossier saves.");
            return null;
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

            return loadedGame;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Chargement de la partie > ERROR");
        }
        return null;
    }

    /**
     * Prepare the beginning of the game : create the players, fill the deck, etc.
     */
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
            System.out.printf("Le joueur %d est-il un joueur virtuel ? (o/n) : ", i + 1);
            String isVirtual = scanner.nextLine().trim().toLowerCase();

            System.out.printf("Prénom du joueur %d : ", i + 1);
            String firstName = scanner.nextLine();
            System.out.printf("Nom du joueur %d : ", i + 1);
            String lastName = scanner.nextLine();

            if (isVirtual.equals("o")) {
                System.out.printf("Quelle stratégie pour le joueur virtuel %d ? (1: Aléatoire, 2: Défensive, 3: Agressive) : ", i + 1);
                String strategyChoice = scanner.nextLine().trim();
                switch (strategyChoice) {
                    case "1" -> this.players.add(new VirtualPlayer(firstName, lastName, new RandomStrategy()));
                    case "2" -> this.players.add(new VirtualPlayer(firstName, lastName, new DefensiveStrategy()));
                    case "3" -> this.players.add(new VirtualPlayer(firstName, lastName, new AgressiveStrategy()));
                    default -> {
                        System.out.println("Choix invalide, stratégie aléatoire par défaut.");
                        this.players.add(new VirtualPlayer(firstName, lastName, new RandomStrategy()));
                    }
                }

                System.out.printf(">>> Joueur virtuel n°%d créé\n", i + 1);
                continue;
            }


            this.players.add(new Player(firstName, lastName));
            System.out.printf(">>> Joueur n°%d créé : %s %s\n", i + 1, firstName, lastName);
        }

        System.out.print("Deck > Initialisation...\n");
        this.generalDeck.fill();
        System.out.print("Deck > Mélange...\n");
        this.generalDeck.shuffle();
        if (this.players.size() == 3) { //2 trophies when there are 3 players
        	System.out.println("Trophies > Prendre les 2 premières...");
            this.trophyCards.addAll(this.generalDeck.deal(2));
            System.out.println("Les trophées sont : " + this.trophyCards + ".");
        } else { //1 trophy when there are 4 players
        	System.out.println("Trophies > Prendre la première...");
            this.trophyCards.addAll(this.generalDeck.deal(1));
            System.out.println("Le trophée est : " + this.trophyCards + ".");
        }
        System.out.print("Deck > OK\n");
    }

    /**
     * Play a round in 3 phases, like in the rules : deal cards, make offers, and take cards.
     */
    public void playRound() {
        // deal & make offer
        for (Player player : this.players) {
            List<Card> cards;
            if (player.getCurrentOffer() == null) { //first round
                cards = this.generalDeck.deal(2);
            } else { // other rounds
                cards = this.restOfCards.deal(2);
            }
            System.out.println("\n" + player + " pioche 2 cartes.");
            player.makeOffer(cards.get(0), cards.get(1));
        }

        // take
        Comparator<Player> playerComparator =
                Comparator.comparingInt((Player p) ->
                                p.getCurrentOffer().getCard(true).getFaceValue()
                        )
                        .reversed()
                        .thenComparing(
                                Comparator.comparing((Player p) ->
                                        p.getCurrentOffer().getCard(true).getSuit()
                                )
                        );

        LinkedList<Player> playersAwaitingChoice = this.players.stream()
                .sorted(playerComparator)
                .collect(Collectors.toCollection(LinkedList::new));

        Player lastPlayerSelected = playersAwaitingChoice.removeFirst();

        while (true) {
            Player selection = lastPlayerSelected.chooseOffer(this.players);

            if (playersAwaitingChoice.remove(selection)) {
                lastPlayerSelected = selection;
            } else if (!playersAwaitingChoice.isEmpty()) {
                lastPlayerSelected = playersAwaitingChoice.removeFirst();
            } else {
                break;
            }
        }

        // re-fill
        if (!this.generalDeck.isEmpty()) {
            for (Player player : players) {
                this.restOfCards.add(player.getCurrentOffer().takeCard());
                this.restOfCards.add(this.generalDeck.deal());
            }
            this.restOfCards.shuffle();
        }
    }

    /**
     * If the {@link Deck}s (the general and restOfCards) are empty, we determine the winner. If it is not the case, the game continues.
     *
     * @return true if the game is over.
     */
    public boolean endGameIfNecessary() {
        if (this.generalDeck.isEmpty() && this.restOfCards.isEmpty()) {
            for (Player player : this.players) {
                player.getJest().addCard(player.getCurrentOffer().takeCard());
            }
            determineWinner();
            return true;
        }
        return false;
    }

    /**
     * Award trophies to good players.
     */
    private void awardTrophies() {
        List<PlayerTrophyCard> trophyCardsToGive = new ArrayList<>();
        for (Card trophyCard : trophyCards) {
            var test = trophyCard.getTrophy().getWinner(players);
            System.out.println("Trophy > " + trophyCard + ", " + trophyCard.getTrophy().getName() + " won by " + test + "\n----------");
            trophyCardsToGive.add(new PlayerTrophyCard(test, trophyCard));
        }
        trophyCards.clear();
        trophyCardsToGive.forEach(trophyToGive -> {
            trophyToGive.player.getJest().addCard(trophyToGive.card);
            }
        );
    }

    /**
     * Determine the winner, comparing the scores of the players.
     */
    private void determineWinner() {
        this.awardTrophies();

        List<PlayerScore> ranking = new ArrayList<>();

        for (Player player : this.players) {
        	System.out.println("Jest de " + player + " : " + player.getJest());
            PlayerScore playerScore = new PlayerScore(player, player.getJest().getScore());
            System.out.println("Player " + player + " has score " + playerScore.score() + "\n----------");
            ranking.add(playerScore);
        }

        ranking = ranking.stream()
                .sorted(Comparator.comparingInt(PlayerScore::score).reversed())
                .collect(Collectors.toList());

        PlayerScore winner = ranking.getFirst();
        System.out.println("Winner   : " + winner.player + " with " + winner.score + " points");
        for (int i = 1; i < ranking.size(); i++) {
            PlayerScore ps = ranking.get(i);
            System.out.println("Player " + (i+1) + " : " + ps.player + " with " + ps.score + " points");
        }
        System.out.println("----------");
    }

    public Deck getGeneralDeck() {
        return this.generalDeck;
    }

    public Deck getRestOfCards() {
        return this.restOfCards;
    }

    /**
     * Suggest the player if he wants to save his game ("1" to continue, "2" to save).
     * @return true if the game is saved or false if the game is not saved.
     */
    public boolean suggestSaving() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Souhaitez-vous passer au tour suivant (1) ou sauvegarder la partie (2) : ");
        int option = Integer.parseInt(scanner.nextLine());
        if (option == 2) {
            this.save();
            return true;
        }
        return false;
    }

    /**
     * Save the game.
     */
    private void save() {

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

    private record PlayerScore(Player player, int score) {}
    
    private record PlayerTrophyCard(Player player, Card card) {}

}
