package nl.mprog.apps.hangman11079592.model;

import java.util.ArrayList;
import java.util.Arrays;

import nl.mprog.apps.hangman11079592.basemodel.Figure;

/**
 * This class handles the Gameplay game and all its dependencies.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Gameplay {

    protected Dictionary dictionary;

    protected String secretWord;

    protected String displayWord; // This is the word/underscores the player sees

    protected ArrayList<Character> tries;

    protected ArrayList<Character> correct;

    protected Figure figure;

    public Gameplay() {
        HangMan.getHistoryInstance().startNewSequence();
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Start a game. If a game is already started, it will reset with a new word
     *
     */
    public void startGame() {
        String word = this.dictionary.getRandomWord();
        this.loadGame(word);
    }

    public int enterChar(char guess) {
        // When the character has already been tried
        // make it possible to notify the player
        if (this.tries.contains(guess)) {
            return HangMan.DUPLICATE_ATTEMPT;
        }

         // Add this character to the list of tried characters
        this.tries.add(guess);

        // If the guess is not in the secret word let the figure go to the next stage
        if (!this.isInSecretWord(guess)) {
            this.figure.nextStage();

            // If the figure ran out of stages the word was not guessed
            // and the game has been list
            if (this.figure.isComplete()) {
                this.displayWord = this.secretWord;
                HangMan.getHistoryInstance().startNewSequence();
                return HangMan.GAME_LOST;
            }

            return HangMan.GUESS_FAIL;
        }

        this.correct.add(guess);

        this.buildDisplayWord();
        // When the last guessed character resulted in
        // a word without any underscore characters in it
        // the the player has won the game
        if (this.displayWord.indexOf("_") == -1) {
            this.displayWord = this.secretWord;
            HangMan.getHistoryInstance().newWordWon(this.secretWord);

            // Make sure that each won game will be persisted in the history
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

    protected void loadGame(String secretWord) {
        this.correct = new ArrayList();
        this.tries = new ArrayList();
        this.resetGame(secretWord);
    }

    /**
     * Reset the game to it's basic values with a new secret word
     *
     * @param secretWord The secret word to guess
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
