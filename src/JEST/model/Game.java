package JEST.model;

import JEST.controller.GameController;
import JEST.controller.PlayerRouter;
import JEST.model.cards.Card;
import JEST.model.cards.Deck;
import JEST.model.cards.DeckType;
import JEST.model.virtualPlayer.AgressiveStrategy;
import JEST.model.virtualPlayer.DefensiveStrategy;
import JEST.model.virtualPlayer.RandomStrategy;
import JEST.model.virtualPlayer.VirtualPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class starts a game, load or save one, play a round, and determines the winner.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Game instance;
    private List<Player> players;
    private Deck generalDeck;
    private Deck restOfCards;
    private List<Card> trophyCards;
    private transient GameController gameController;
    private transient PlayerRouter playerRouter;

    /**
     * Private constructor for Singleton pattern.
     */
    private Game() {
        this.players = new ArrayList<>();
        this.generalDeck = Deck.getInstance(DeckType.GENERAL);
        this.restOfCards = Deck.getInstance(DeckType.REST_OF_CARDS);
        this.trophyCards = new ArrayList<>();
    }

    /**
     * We use the Singleton design pattern, to guarantee that there is only one instance of this class.
     *
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
     *
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
    public static Game load(GameController gameController) {

        File savesDir = new File("saves");
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }

        File[] files = savesDir.listFiles((d, name) -> name.toLowerCase().endsWith(".jest"));
        if (files == null || files.length == 0) {
            gameController.displayMessage("Aucune sauvegarde .jest trouvée dans le dossier saves.", GameController.MessageType.ERROR);
            return null;
        }

        List<String> fileNames = new ArrayList<>();
        for (File f : files) {
            fileNames.add(f.getName());
        }

        int choice = gameController.askSelectFileToLoad(fileNames);
        File selected = files[choice];

        try (FileInputStream fis = new FileInputStream(selected);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Game loadedGame = (Game) ois.readObject();
            loadedGame.setGameController(gameController);

            return loadedGame;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            gameController.displayMessage("Chargement de la partie > ERROR", GameController.MessageType.ERROR);
        }
        return null;
    }

    /**
     * Get the current game interface.
     *
     * @return the game interface
     */
    public GameController getGameController() {
        return this.gameController;
    }

    /**
     * Set the game interface (console or GUI).
     *
     * @param gameController the interface to use
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;

    }

    /**
     * Get the player router.
     *
     * @return the player router
     */
    public PlayerRouter getPlayerRouter() {
        return this.playerRouter;
    }

    /**
     * Set the player router for player-specific operations.
     *
     * @param playerRouter the router to use for player-specific interactions
     */
    public void setPlayerRouter(PlayerRouter playerRouter) {
        this.playerRouter = playerRouter;
    }

    /**
     * Add a human player with console interface by default.
     *
     * @param firstName the player's first name
     * @param lastName  the player's last name
     */
    public void addHumanPlayer(String firstName, String lastName) {
        this.addHumanPlayer(firstName, lastName, Player.InterfaceType.CONSOLE);
    }

    /**
     * Add a human player with specified interface type.
     *
     * @param firstName     the player's first name
     * @param lastName      the player's last name
     * @param interfaceType the player's preferred interface type
     */
    public void addHumanPlayer(String firstName, String lastName, Player.InterfaceType interfaceType) {
        Player player = new Player(firstName, lastName, interfaceType);
        this.players.add(player);
    }

    /**
     * Add a virtual player with specified strategy.
     *
     * @param firstName the player's first name
     * @param lastName  the player's last name
     * @param strategy  the strategy type for the virtual player
     */
    public void addVirtualPlayer(String firstName, String lastName, String strategy) {
        VirtualPlayer player;
        switch (strategy) {
            case "1" -> player = new VirtualPlayer(firstName, lastName, new RandomStrategy());
            case "2" -> player = new VirtualPlayer(firstName, lastName, new DefensiveStrategy());
            case "3" -> player = new VirtualPlayer(firstName, lastName, new AgressiveStrategy());
            default -> player = new VirtualPlayer(firstName, lastName, new RandomStrategy());
        }
        this.players.add(player);
    }

    /**
     * Prepare the beginning of the game : create the players, fill the deck, etc.
     */
    public void setup() {

        if (this.gameController == null) {
            throw new IllegalStateException("GameController must be set before calling setup()");
        }

        int playerNumber = this.gameController.askNumberOfPlayers();

        for (int i = 0; i < playerNumber; i++) {
            boolean isVirtual = this.gameController.askIsVirtualPlayer(i + 1);

            String firstName = this.gameController.askFirstName(i + 1);
            String lastName = this.gameController.askLastName(i + 1);

            if (isVirtual) {
                String strategyChoice = this.gameController.askStrategy(i + 1);
                this.addVirtualPlayer(firstName, lastName, strategyChoice);
            } else {
                this.addHumanPlayer(firstName, lastName);
            }

            this.gameController.displayPlayerCreated(i + 1, firstName, lastName, isVirtual);
        }

        this.initializeDeckAndTrophies();
    }

    /**
     * Initialize deck and trophies only (players are already added).
     * Used when players are configured through GUI.
     */
    public void initializeDeckAndTrophies() {
        if (this.gameController == null) {
            throw new IllegalStateException("GameController must be set before calling initializeDeckAndTrophies()");
        }

        this.gameController.displayMessage("Deck > Initialisation...", GameController.MessageType.NORMAL);
        this.generalDeck.fill();
        this.gameController.displayMessage("Deck > Mélange...", GameController.MessageType.NORMAL);
        this.generalDeck.shuffle();
        if (this.players.size() == 3) { //2 trophies when there are 3 players
            this.trophyCards.addAll(this.generalDeck.deal(2));
        } else { //1 trophy when there are 4 players
            this.trophyCards.addAll(this.generalDeck.deal(1));
        }
        this.gameController.displayTrophyCards(this.trophyCards);
        this.gameController.displayMessage("Deck > OK", GameController.MessageType.NORMAL);
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
            this.gameController.displayMessage("\n" + player + " pioche 2 cartes.", GameController.MessageType.NORMAL);

            // Display strategy message for virtual players
            if (player instanceof VirtualPlayer) {
                this.gameController.displayMessage(String.format("%s a fait une offre via une stratégie %s.", player, ((VirtualPlayer) player).getStrategy()), GameController.MessageType.NORMAL);
            }

            player.makeOffer(cards.get(0), cards.get(1), this.playerRouter);
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
            // Display strategy message for virtual players
            if (lastPlayerSelected instanceof VirtualPlayer) {
                this.gameController.displayMessage(String.format("\n%s a choisi une offre via une stratégie %s.", lastPlayerSelected, ((VirtualPlayer) lastPlayerSelected).getStrategy()), GameController.MessageType.NORMAL);
            }

            Player selection = lastPlayerSelected.chooseOffer(this.players, this.playerRouter);

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

    public List<Card> getTrophyCards() {
        return trophyCards;
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
            this.gameController.displayMessage("Trophy > " + trophyCard + ", " + trophyCard.getTrophy().getName() + " won by " + test + "\n----------", GameController.MessageType.NORMAL);
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
            this.gameController.displayMessage("Jest de " + player + " : " + player.getJest(), GameController.MessageType.NORMAL);
            PlayerScore playerScore = new PlayerScore(player, player.getJest().getScore());
            this.gameController.displayMessage("Player " + player + " has score " + playerScore.score() + "\n----------", GameController.MessageType.NORMAL);
            ranking.add(playerScore);
        }

        ranking = ranking.stream()
                .sorted(Comparator.comparingInt(PlayerScore::score).reversed())
                .collect(Collectors.toList());

        StringBuilder rankingText = new StringBuilder();
        PlayerScore winner = ranking.getFirst();
        rankingText.append("Winner   : ").append(winner.player).append(" with ").append(winner.score).append(" points\n");
        for (int i = 1; i < ranking.size(); i++) {
            PlayerScore ps = ranking.get(i);
            rankingText.append("Player ").append(i + 1).append(" : ").append(ps.player).append(" with ").append(ps.score).append(" points\n");
        }
        rankingText.append("----------");

        this.gameController.displayWinner(rankingText.toString());
    }

    public Deck getGeneralDeck() {
        return this.generalDeck;
    }

    public Deck getRestOfCards() {
        return this.restOfCards;
    }

    /**
     * Suggest the player if he wants to save his game ("1" to continue, "2" to save).
     *
     * @return true if the game is saved or false if the game is not saved.
     */
    public boolean suggestSaving() {
        boolean wantsSave = this.gameController.askSaveGame();
        if (wantsSave) {
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
            String filenameInput = this.gameController.askSaveFileName();
            if (isValidFilename(filenameInput)) {
                filename = filenameInput;
            } else {
                this.gameController.displayMessage("Nom de fichier invalide. Veuillez utiliser uniquement des lettres et des chiffres.", GameController.MessageType.ERROR);
            }
        }

        File outFile = new File(savesDir, filename + ".jest");

        this.gameController.displayMessage("Sauvegarde de la partie > En cours...", GameController.MessageType.NORMAL);
        try (FileOutputStream fos = new FileOutputStream(outFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);

            this.gameController.displayMessage("Sauvegarde de la partie > OK", GameController.MessageType.INFORMATION);
        } catch (IOException ex) {
            ex.printStackTrace();
            this.gameController.displayMessage("Sauvegarde de la partie > ERROR", GameController.MessageType.ERROR);
        }

    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * A record to hold player and their score for ranking.
     */
    private record PlayerScore(Player player, int score) {
    }

    /**
     * A record to hold player and their trophy card.
     */
    private record PlayerTrophyCard(Player player, Card card) {
    }

}
