package nl.mprog.apps.hangman11079592.basemodel;

import java.io.InputStream;
import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.exception.DictionaryReaderException;

/**
 * An abstract reader for collecting data from a file.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public abstract class DictionaryReader {
    /**
     * The input stream for this reader
     */
    protected InputStream inputStream;

    /**
     * The list of words
     */
    protected ArrayList<String> words;

    /**
     * Flag set when the dictionary has been loaded
     */
    protected boolean dictionaryIsLoaded;

    /**
     * Initialize the reader with an InputStrem
     * @param inputStream
     */
    public DictionaryReader(InputStream inputStream) {
        this.inputStream = inputStream;
        this.words = new ArrayList();
        this.dictionaryIsLoaded = false;
    }

    /**
     * Load the dictionary file into the memory
     */
    public abstract void loadFile() throws DictionaryReaderException;

    /**
     * Load the content from the file
     */
    public abstract ArrayList<String> getContents() throws DictionaryReaderException;

    /**
     * Get the list of words with a specific length range
     *
     * @param minimumLength The minimum word length
     * @param maximumLength The maximum word length
     */
    public abstract ArrayList<String> getWords(int minimumLength, int maximumLength);
}
