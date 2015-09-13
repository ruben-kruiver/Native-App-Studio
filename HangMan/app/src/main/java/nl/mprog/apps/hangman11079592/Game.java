package nl.mprog.apps.hangman11079592;

import java.util.ArrayList;
import java.util.Arrays;

import nl.mprog.apps.hangman11079592.exceptions.DuplicateAttemptException;
import nl.mprog.apps.hangman11079592.exceptions.FigureCompleteException;
import nl.mprog.apps.hangman11079592.exceptions.GameOverException;
import nl.mprog.apps.hangman11079592.exceptions.GameWonException;
import nl.mprog.apps.hangman11079592.interfaces.Figure;

/**
 * This class handles the game flow of the HangMan Game
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Game {
    /**
     * The secret word for this game
     */
    protected String secret_word;

    /**
     * The string that can be displayed to the player
     */
    protected StringBuilder display_word;

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
     * Initialize a new Game with a secret word that needs to be guessed with
     * a default StickFigure
     *
     * @param secret_word The secret word to guess
     */
    public Game(String secret_word) {
        this.figure = new StickFigure();
        this.correct = new ArrayList(); // Allow for flexible length of correct guessed chars
        this.tries = new ArrayList();
        this.resetGame(secret_word);
    }

    /**
     * Set a different StickFigure for this game.
     *
     * @param figure The new StickFigure
     */
    public void setNewFigure(Figure figure) throws GameOverException {
        try {
            // Get the current stage of this game
            int current_stage = this.figure.getCurrentStage();

            // Set the new figure
            this.figure = figure;

            // Set the previous stage for the new figure
            this.figure.reset(current_stage);
        } catch (FigureCompleteException e) {
          throw new GameOverException();
        }
    }

    /**
     * Get the figure used in the current game
     *
     * @return The current figure
     */
    public Figure getFigure() {
        return this.figure;
    }

    /**
     * Get the word to display to the player
     *
     * @return The displayable word
     */
    public String getDisplayWord() {
        return this.display_word.toString();
    }

    /**
     * Reset the game to it's basic values with a new secret word
     *
     * @param secret_word The secret wordt to guess
     */
    public void resetGame(String secret_word) {
        this.secret_word = secret_word;
        this.tries.clear();
        this.correct.clear();
        this._createBuildDisplayWord();
        this.figure.reset();
    }

    /**
     * Enter a new character that is guesses
     *
     * @return Returns TRUE if the character occurres in the secret word, FALSE otherwise
     * @throws DuplicateAttemptException Thrown when the attempted char was already in the list
     * @throws GameOverException Thrown when there are no more stages available in the figure
     */
    public boolean enterChar(char guess) throws DuplicateAttemptException, GameOverException, GameWonException {
        /**
         * Check if the character has already been tried
         */
        if (this.tries.contains(guess)) {
            throw new DuplicateAttemptException();
        }

        /**
         * Add this character to the list of tried characters
         */
        this.tries.add(guess);
        // If the guess is not in the secret word let the figure go to the next stage
        if (!this._isInSecretWord(guess)) {
            try {
                this.figure.nextStage();
                return false;
            } catch (FigureCompleteException e) {
                // When the figure is completed the game is over
                throw new GameOverException();
            }
        }

        this.correct.add(guess);

        this._createBuildDisplayWord();

        if (this.display_word.indexOf("_") == -1) {
            throw new GameWonException();
        }

        return true;
    }

    /**
     * Build the display word based on the guessed characters and the secret word
     */
    protected void _createBuildDisplayWord() {
        this.display_word = this._buildUnderscoreString();

        for (Character correct : this.correct){
            for (int index = this.secret_word.indexOf(correct);
                 index >= 0;
                 index = this.secret_word.indexOf(correct, index + 1))
            {
                int act_index = (index * 2);
                this.display_word.setCharAt(act_index, correct);
            }
        }
    }

    /**
     * Validate the character with the secret word
     * @return Returns TRUE when the character is present in the secret word, FALSE otherwise
     */
    protected boolean _isInSecretWord(char guess) {
        return this.secret_word.indexOf(guess) > -1;
    }

    /**
     * Build an underscored string with the length of the secret word
     * @return The build string from underscores
     */
    protected StringBuilder _buildUnderscoreString() {
        char[] underscores = new char[this.secret_word.length()];
        Arrays.fill(underscores, '_');

        StringBuilder builder = new StringBuilder();
        for (char c : underscores) {
            builder.append(c);
            builder.append(' ');
        }

        // Return the underscored string without the last space
        return builder.deleteCharAt(builder.length() - 1);
    }
}
