package JEST.model.cards;

/**
 * This enum class permits to define the two types of {@link Deck} fixed by the design pattern Multiton : the general and the "restOfCards".
 */
public enum DeckType {
	/**
	 * This is the deck which contains all the cards of the game at the beginning.
	 */
	GENERAL,
	
	/**
	 * This deck is composed by the offer cards unused in the last round and a certain number of cards of the general deck, equals to the number of players of the game.
	 * It is filled after each round. 
	 */
	REST_OF_CARDS;
}
