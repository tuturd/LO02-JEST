/**
 * Core model package containing the business logic of the JEST game.
 * <p>
 * This package implements the game mechanics, player management, and game state.
 * The main classes are:
 * </p>
 * <ul>
 *   <li>{@link JEST.model.Game} - Singleton game manager handling rounds, saves, and game state</li>
 *   <li>{@link JEST.model.Player} - Represents a player (human or virtual) with their cards and Jest</li>
 *   <li>{@link JEST.model.cards} - Card system including deck, suits, and trophy cards</li>
 *   <li>{@link JEST.model.virtualPlayer} - AI strategies for virtual players</li>
 * </ul>
 */
package JEST.model;