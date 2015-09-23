package nl.mprog.apps.hangman11079592.model.reader;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.basemodel.DictionaryReader;
import nl.mprog.apps.hangman11079592.model.Dictionary;

/**
 * Load a dictionary from a XMLReader file source. The loaded XMLReader file should
 * be have a nodes called "item" that contain a word for the dictionary list
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class XMLReader extends DictionaryReader {

    protected String nodename = "item";

    protected XmlPullParser parser;

    protected static Integer longestWordLength; // We make this static because the current
                                                // design doesn't allow for the dictionary
                                                // to be changed easily. Thus the size of
                                                // the list won't change

    protected XMLReader(InputStream inputStream, int minimumLength, int maximumLength){
        super(inputStream, minimumLength, maximumLength);
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

        // Reset the total words in the list to 0 before reading the dictionary
        XMLReader.longestWordLength = 0;

        XMLReader reader = new XMLReader(inputStream, minimumLength, maximumLength);
        reader.initXMLParser();
        reader.parseFile();

        Dictionary dictionary = new Dictionary(reader.words);
        return dictionary;
    }

    /**
     * Get the size of the longest word in the dictionary. This value is stored
     * in the reader because the dictionary doesn't have to complete list of words
     * and therefore cannot ensure the length of the actual longest word in the file
     */
    public static Integer getLongestWordLength() {
        return XMLReader.longestWordLength;
    }

    protected void initXMLParser() throws XmlPullParserException {
        this.parser = Xml.newPullParser();
        this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        this.parser.setInput(this.inputStream, null);
    }

    protected void parseFile() throws IOException, XmlPullParserException {
        int eventType = this.parser.getEventType();
        String currentWord;

        this.words = new ArrayList();

        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType != XmlPullParser.START_TAG
                    || !this.parser.getName().equalsIgnoreCase(this.nodename)) {
                eventType = this.parser.next();
                continue;
            }

            currentWord = this.parser.nextText();

            // Add the word to the list when it falls within the requested ranges
            if (currentWord != null) {
                if (currentWord.length() >= this.minimumLength
                    && currentWord.length() <= this.maximumLength) {
                    this.words.add(currentWord);
                }

                // Store the length of the longest word
                if (currentWord.length() > XMLReader.longestWordLength) {
                    XMLReader.longestWordLength = currentWord.length();
                }
            }

            eventType = this.parser.next();
        }
    }
}
