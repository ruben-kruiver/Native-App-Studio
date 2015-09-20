package nl.mprog.apps.hangman11079592.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import nl.mprog.apps.hangman11079592.basemodel.DictionaryReader;
import nl.mprog.apps.hangman11079592.basemodel.Figure;
import nl.mprog.apps.hangman11079592.exception.DictionaryReaderException;

/**
 * This class handles the Gameplay game and all its dependencies.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Gameplay {
    /**
     * The dictionary file for the instance
     */
    protected Dictionary dictionary;

    /**
     * The secret word for this game
     */
    protected String secretWord;

    /**
     * The string that can be displayed to the player
     */
    protected String displayWord;

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
    protected int minimumChars;

    /**
     * The maximum number of characters for the word
     */
    protected int maximumChars;

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
     * @param minimumChars
     * @param maximumChars
     */
    public void setWordLength(int minimumChars, int maximumChars) {
        this.minimumChars = minimumChars;
        this.maximumChars = maximumChars;
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
        String word = this.dictionary.getRandomWord(this.minimumChars, this.maximumChars);
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
                this.displayWord = this.secretWord;
                HangMan.getHistoryInstance().startNewSequence();
                return HangMan.GAME_LOST;
            }

            return HangMan.GUESS_FAIL;
        }

        this.correct.add(guess);

        this.buildDisplayWord();

        if (this.displayWord.indexOf("_") == -1) {
            this.displayWord = this.secretWord;
            HangMan.getHistoryInstance().newWordWon(this.secretWord);

            // Make sure that each item will be persisted
            HangMan.getHistoryInstance().storeHistory();
            return HangMan.GAME_WON;
        }

        return HangMan.GUESS_OK;
    }

    /**
     * Get the word that can be displayed to the user. Unknown characters
     * are replaced by underscores.
     */
    public String getDisplayWord() {
        return this.displayWord.toString();
    }

    /**
     * Load a new game instance
     */
    protected void loadGame(String secretWord) {
        this.correct = new ArrayList(); // Allow for flexible length of correct guessed chars
        this.tries = new ArrayList();
        this.resetGame(secretWord);
    }

    /**
     * Reset the game to it's basic values with a new secret word
     *
     * @param secretWord The secret wordt to guess
     */
    protected void resetGame(String secretWord) {
        this.secretWord = secretWord;
        this.tries.clear();
        this.correct.clear();
        this.buildDisplayWord();
        this.figure.reset();
    }

    /**
     * Build the display word based on the guessed characters and the secret word
     */
    protected void buildDisplayWord() {
        StringBuilder underscoreString = this.buildUnderscoreString();

        // Replace the underscores with the guessed characters in the word
        for (int index = 0; index < this.secretWord.length(); index++) {
            Character character = this.secretWord.charAt(index);

            if (this.correct.contains(Character.toLowerCase(character))) {
                // We need to account for the spaces between the underscores
                int underscore_index = index * 2;
                underscoreString.setCharAt(underscore_index, character);
            }
        }

        this.displayWord = underscoreString.toString();
    }

    /**
     * Validate the character with the secret word
     * @return Returns TRUE when the character is present in the secret word, FALSE otherwise
     */
    protected boolean isInSecretWord(char guess) {
        return this.secretWord.toLowerCase().indexOf(guess) > -1;
    }

    /**
     * Build an underscored string with the length of the secret word
     * @return The build string from underscores
     */
    protected StringBuilder buildUnderscoreString() {
        char[] underscores = new char[this.secretWord.length()];
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
