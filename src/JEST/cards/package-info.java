/**
 * This package contains all the classes linked to the cards in the game :
 * A {@link Card} is a {@link SuitCard} or a {@link JokerCard}.
 * An offer is composed by 2 offercards, who are face-up or face-down cards.
 * The deck is general or the rest of the cards unused in the last round.
 * {@link CardVisitor} and {@link ScoreVisitor} permit the separation of data from a class's processing, access control.
 * The Jest is the list of the cards that the player gets during the rounds.
 */
package JEST.cards;