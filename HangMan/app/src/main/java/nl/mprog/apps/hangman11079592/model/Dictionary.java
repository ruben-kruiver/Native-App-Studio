package nl.mprog.apps.hangman11079592.model;

import java.util.ArrayList;
import java.util.Random;

import nl.mprog.apps.hangman11079592.basemodel.DictionaryReader;
import nl.mprog.apps.hangman11079592.exception.DictionaryReaderException;

/**
 * This class handles the Dictionary file for the Gameplay game
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Dictionary {
    /**
     * The DictionaryReader for this dictionary to use to get the contents from a dictionary file
     */
    protected DictionaryReader reader;

    /**
     * The limited word list for this game based on the value of the maximum word length
     */
    protected ArrayList<String> limited_word_list;

    /**
     * The current minimum word length stored in the limited list
     */
    protected int current_minimum_word_length;

    /**
     * The current maximum word length stored in the limited list
     */
    protected int current_maximum_word_length;


    /**
     * Initialize the Dictionary
     *
     * @param reader The reader for this dictionary
     */
    public Dictionary(DictionaryReader reader) {

        this.reader = reader;
    }

    /**
     * Get a random word from the dictionary that that fits within the supplied limits
     *
     * @param minimumChars The minimum number of chars for the word
     * @param maximumChars The maximum number of chars for the word
     * @return The found word
     */
    public String getRandomWord(int minimumChars, int maximumChars) throws DictionaryReaderException {
        this.reader.loadFile();
        ArrayList<String> words = this.getLimitedWordList(minimumChars, maximumChars);

        Random rand = new Random();
        int index = rand.nextInt(words.size());

        return words.get(index);
    }

    /**
     * Get the longest word from the dictionary
     *
     * @return The found word
     */
    public String getLongestWord() throws DictionaryReaderException {
        this.reader.loadFile();
        ArrayList<String> words = this.reader.getContents();

        int max = 0;
        String longestWord = "";

        for (String word : words) {
            if (word.length() > max) {
                max = word.length();
                longestWord = word;
            }
        }

        return longestWord;
    }

    /**
     * Get the reader used by this dictionary
     * @return The dictionary
     */
    public DictionaryReader getReader() {
        return this.reader;
    }


    /**
     * Load the limited word list based on the settings word length. This will be cached
     * for performance improvements.
     */
    protected ArrayList<String> getLimitedWordList(int minimum_length, int maximum_length) {
        // If the list was not created or doesn't match the required length range (re)build the list
        if (this.limited_word_list == null
                || this.current_maximum_word_length == minimum_length
                || this.current_maximum_word_length == maximum_length) {
            this.limited_word_list = this.reader.getWords(minimum_length, maximum_length);
        }

        return this.limited_word_list;
    }
}
