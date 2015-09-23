package nl.mprog.apps.hangman11079592.basemodel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.model.Dictionary;

/**
 * An abstract reader for collecting data from a file.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public abstract class DictionaryReader {

    protected InputStream inputStream;

    protected int minimumLength;

    protected int maximumLength;

    protected ArrayList<String> words;

    /**
     * Set the constructor protected to encourage the use of the static loadDictionary method
     * @param inputStream
     * @param minimumLength
     * @param maximumLength
     */
    protected DictionaryReader(InputStream inputStream, int minimumLength, int maximumLength) {
        this.inputStream = inputStream;
        this.minimumLength = minimumLength;
        this.maximumLength = maximumLength;
    }

    /**
     * Create a new Dictionary containing the words with length between the minimumLength
     * and the maximumLength
     *
     * @param inputStream
     * @param minimumLength
     * @param maximumLength
     * @return The Dictionary with the limited words list
     */
    public static Dictionary loadDictionary(InputStream inputStream, int minimumLength, int maximumLength)
            throws IOException, XmlPullParserException {
        // This method should be implemented by each of it's descendants
        throw new IllegalStateException();
    }

    public static Integer getLongestWordLength() {
        // This method should be implemented by each of it's descendants
        throw new IllegalStateException();
    }
}
