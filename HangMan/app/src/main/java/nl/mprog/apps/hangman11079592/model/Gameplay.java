package nl.mprog.apps.hangman11079592.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import nl.mprog.apps.hangman11079592.exception.DictionaryReaderException;
import nl.mprog.apps.hangman11079592.basemodel.DictionaryReader;
import nl.mprog.apps.hangman11079592.basemodel.Figure;

/**
 * This class handles the Gameplay game and all its dependencies.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Gameplay implements Serializable {
    /**
     * The dictionary file for the instance
     */
    protected Dictionary dictionary;

    /**
     * The secret word for this game
     */
    protected String secret_word;

    /**
     * The string that can be displayed to the player
     */
    protected String display_word;

    /**
     * The list of characters that have been tried
     */
    protected ArrayList<Character> tries;

    /**
     * The list of correctly guessed characters
     */
    protected ArrayList<Character> correct;

    /**
     * The StickFigure for this game
     */
    protected Figure figure;

    /**
     * The minimum number of characters for the word
     */
    protected int minimum_chars;

    /**
     * The maximum number of characters for the word
     */
    protected int maximum_chars;

    /**
     * Create a new gameplay
     */
    public Gameplay() {
        HangMan.getHistoryInstance().startNewSequence();
    }

    /**
     * Set the Figure for this game
     * @param figure
     */
    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    /**
     * Load the dictionary from a DictionaryReader source
     *
     * @param reader The reader for the dictionary
     */
    public void setReader(DictionaryReader reader) {
        this.dictionary = new Dictionary(reader);
    }

    /**
     * Set the boundries for the words that can be played
     * @param minimum_chars
     * @param maximum_chars
     */
    public void setWordLength(int minimum_chars, int maximum_chars) {
        this.minimum_chars = minimum_chars;
        this.maximum_chars = maximum_chars;
    }

    /**
     * Get the Dictionary instance used in this game
     */
    public Dictionary getDictionary() {
        return this.dictionary;
    }

    /**
     * Start a game. If a game is already started, it will reset with a new game
     *
     */
    public void startGame() throws DictionaryReaderException {
        String word = this.dictionary.getRandomWord(this.minimum_chars, this.maximum_chars);
        this.loadGame(word);
    }

    /**
     * Enter the char the player has guessed and validate it's outcome
     * @param guess
     */
    public int enterChar(char guess) {
        /**
         * Check if the character has already been tried
         */
        if (this.tries.contains(guess)) {
            return HangMan.DUPLICATE_ATTEMPT;
        }

        /**
         * Add this character to the list of tried characters
         */
        this.tries.add(guess);
        // If the guess is not in the secret word let the figure go to the next stage
        if (!this.isInSecretWord(guess)) {
            this.figure.nextStage();

            if (this.figure.isComplete()) {
                this.display_word = this.secret_word;
                HangMan.getHistoryInstance().startNewSequence();
                return HangMan.GAME_LOST;
            }

            return HangMan.GUESS_FAIL;
        }

        this.correct.add(guess);

        this.createBuildDisplayWord();

        if (this.display_word.indexOf("_") == -1) {
            this.display_word = this.secret_word;
            HangMan.getHistoryInstance().newWordWon(this.secret_word);

            // Make sure that each item will be persisted
            HangMan.getHistoryInstance().storeHistory();
            return HangMan.GAME_WON;
        }

        return HangMan.GUESS_OK;
    }

    /**
     * Get the current figure
     */
    public Figure getGameplayFigure() {
        return this.figure;
    }

    /**
     * Get the word that can be displayed to the user. Unknown characters
     * are replaced by underscores.
     */
    public String getDisplayWord() {
        return this.display_word.toString();
    }

    /**
     * Load a new game instance
     */
    protected void loadGame(String secret_word) {
        this.correct = new ArrayList(); // Allow for flexible length of correct guessed chars
        this.tries = new ArrayList();
        this.resetGame(secret_word);
    }

    /**
     * Reset the game to it's basic values with a new secret word
     *
     * @param secret_word The secret wordt to guess
     */
    protected void resetGame(String secret_word) {
        this.secret_word = secret_word;
        this.tries.clear();
        this.correct.clear();
        this.createBuildDisplayWord();
        this.figure.reset();
    }

    /**
     * Build the display word based on the guessed characters and the secret word
     */
    protected void createBuildDisplayWord() {
        StringBuilder underscoreString = this.buildUnderscoreString();

        // Replace the underscores with the guessed characters in the word
        for (int index = 0; index < this.secret_word.length(); index++) {
            Character character = this.secret_word.charAt(index);

            if (this.correct.contains(Character.toLowerCase(character))) {
                // We need to account for the spaces between the underscores
                int underscore_index = index * 2;
                underscoreString.setCharAt(underscore_index, character);
            }
        }

        this.display_word = underscoreString.toString();
    }

    /**
     * Validate the character with the secret word
     * @return Returns TRUE when the character is present in the secret word, FALSE otherwise
     */
    protected boolean isInSecretWord(char guess) {
        return this.secret_word.toLowerCase().indexOf(guess) > -1;
    }

    /**
     * Build an underscored string with the length of the secret word
     * @return The build string from underscores
     */
    protected StringBuilder buildUnderscoreString() {
        char[] underscores = new char[this.secret_word.length()];
        Arrays.fill(underscores, '_');

        // Build a string of an underscore and one empty space
        StringBuilder builder = new StringBuilder();
        for (char c : underscores) {
            builder.append(c);
            builder.append(' ');
        }

        // Return the underscored string without the last space
        return builder.deleteCharAt(builder.length() - 1);
    }
}
