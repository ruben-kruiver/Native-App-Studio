package nl.mprog.apps.hangman11079592;

import java.io.Serializable;
import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.enumerations.Level;
import nl.mprog.apps.hangman11079592.exceptions.DictionaryReaderException;
import nl.mprog.apps.hangman11079592.exceptions.DuplicateAttemptException;
import nl.mprog.apps.hangman11079592.exceptions.GameOverException;
import nl.mprog.apps.hangman11079592.exceptions.GameWonException;
import nl.mprog.apps.hangman11079592.interfaces.DictionaryReader;
import nl.mprog.apps.hangman11079592.interfaces.Figure;

/**
 * This class handles the HangMan game and all its dependencies.
 * The class is final to prevent constructor overloading by-passing
 * the singleton implemented
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 * @// TODO: 12-9-2015 Multiplayer option, Custom keyboard always visible, Auto-fit for large devices
 */
public final class HangMan implements Serializable {
    /**
     * The dictionary file for the instance
     */
    protected Dictionary dictionary;

    /**
     * A list of active games for multiplayer support
     */
    protected ArrayList<Game> games;

    /**
     * The game for this instance
     */
    protected Game game;

    /**
     * The difficulty level
     */
    protected Level difficulty;

    /**
     * Get the instance of HangMan
     * @return The HangMan instance
     */
    public static HangMan getInstance() {
        return HangManLoader.instance;
    }

    /**
     * Load the dictionary from a CSVReader source
     *
     * @param reader The reader for the dictionary
     * @return Returns TRUE when the file is loaded, FALSE if an error occurred
     */
    public void setReader(DictionaryReader reader) {
        this.dictionary = new Dictionary(reader);
    }

    /**
     * Start a game
     *
     */
    public void startGame() throws DictionaryReaderException {
        String word = this.dictionary.getRandomWord(this.difficulty.getMin(), this.difficulty.getMax());
        this.loadGame(word);
    }

    /**
     * Start a game in debugging mode
     *
     */
    public void startGame(String word) {
        this.loadGame(word);
    }

    /**
     * End the current game
     *
     */
    public void endGame() {
        this.game = null;
    }

    /**
     * Enter a character for the game
     */
    public boolean enterChar(char c) throws DuplicateAttemptException, GameOverException, GameWonException {
        return this.game.enterChar(c);
    }

    /**
     * Get the current figure
     */
    public Figure getCurrentFigure() {
        return this.game.getFigure();
    }

    /**
     * Get the current game state
     */
    public String getGameState() {
        return this.game.getDisplayWord();
    }

    /**
     * Set the difficulty level for this game
     *
     * @param level The level
     */
    public void setDifficulty(Level level) {
        this.difficulty = level;
    }

    /**
     * Get the current difficulty setting for this game
     *
     * @return The level
     */
    public Level getDifficulty() {
        return this.difficulty;
    }

    /**
     * Load a new Game instance. Seperate method to make the implementation testable
     */
    protected void loadGame(String word) {
        if (this.game != null) {
            this.game.resetGame(word);
        } else {
            this.game = new Game(word);
        }
    }

    /**
     * The current instance
     */
    private static  class HangManLoader {
        private static final HangMan instance = new HangMan();
    }

    /**
     * Protected constructor for singleton instantiation
     */
    private HangMan() {
        if (HangManLoader.instance != null) {
            throw new IllegalStateException("Already instantiated");
        }

        // Default difficulty level is intermediate
        this.difficulty = Level.INTERMEDIATE;
    }

    /**
     * Prevent reloading after serialization
     * @return HangMan
     */
    @SuppressWarnings("unused")
    private HangMan readResolve() {
        return HangManLoader.instance;
    }
}
