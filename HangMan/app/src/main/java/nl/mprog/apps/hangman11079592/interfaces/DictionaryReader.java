package nl.mprog.apps.hangman11079592.interfaces;

import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.exceptions.DictionaryReaderException;

/**
 * An abstract reader for collecting data from a file. The file should be read
 * into a single dimensional array each containing a word from the dictionary
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public abstract class DictionaryReader {
    /**
     * The filepath
     */
    protected String filepath;

    /**
     * The list of words
     */
    protected ArrayList<String> words;

    /**
     * Initialize with a filepath
     * @param filepath
     */
    public DictionaryReader(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Load the dictionary file into the memory
     */
    public abstract void loadFile() throws DictionaryReaderException;

    /**
     * Load the content from the file
     */
    public abstract ArrayList<String> getContent() throws DictionaryReaderException;

    /**
     * Get the list of words with a specific length range
     *
     * @param min The minimum word length
     * @param max The maximum word length
     */
    public abstract ArrayList<String> getWords(int min, int max);

    /**
     * Get an entry from the file
     */
    public abstract String getEntry(int index) throws DictionaryReaderException;
}
