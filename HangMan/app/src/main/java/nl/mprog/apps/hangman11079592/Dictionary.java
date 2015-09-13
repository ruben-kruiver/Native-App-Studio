package nl.mprog.apps.hangman11079592;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;

import nl.mprog.apps.hangman11079592.exceptions.DictionaryReaderException;
import nl.mprog.apps.hangman11079592.interfaces.DictionaryReader;

/**
 * This class handles the Dictionary file for the HangMan game
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Dictionary {
    /**
     * The location of the dictionary file
     */
    protected DictionaryReader reader;

    /**
     * Initialize the Dictionary
     *
     * @param reader The reader for this dictionary
     */
    public Dictionary(DictionaryReader reader) {
        this.reader = reader;
    }

    /**
     * Get a random word from the dictionary defined with the defined length
     *
     * @return The found word
     */
    public String getRandomWord(int min, int max) throws DictionaryReaderException {
        this.reader.loadFile();
        ArrayList<String> words = this.reader.getWords(min, max);

        Random rand = new Random();
        int index = rand.nextInt(words.size());

        return words.get(index);
    }
}
