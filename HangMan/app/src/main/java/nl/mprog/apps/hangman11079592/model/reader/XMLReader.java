package nl.mprog.apps.hangman11079592.model.reader;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.basemodel.DictionaryReader;
import nl.mprog.apps.hangman11079592.exception.DictionaryReaderException;

/**
 * Load a dictionary from a XMLReader file source. The loaded XMLReader file should
 * contain a dimension of nodes. If more nodes are available per parent
 * the first node will be interpreted to contain the words. The other
 * nodes will be ignored.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class XMLReader extends DictionaryReader {

    /**
     * The name of the word node
     */
    protected String nodename;

    /**
     * The parser for the XML file
     */
    protected XmlPullParser parser;

    /**
     * Initialize the reader with a file location
     * @param inputStream
     */

    public XMLReader(InputStream inputStream) {
        super(inputStream);

        this.nodename = "item";
    }

    @Override
    public void loadFile() throws DictionaryReaderException {
        // Don't try to load the dictionary more than once
        if (this.dictionaryIsLoaded) {
            return;
        }

        try {
            this.initXMLParser();
            this.parseFile();
            this.dictionaryIsLoaded = true;
        } catch (IOException | XmlPullParserException ex) {
          throw new DictionaryReaderException();
        }
    }

    @Override
    public ArrayList<String> getContents() throws DictionaryReaderException {
        return this.words;
    }

    @Override
    public ArrayList<String> getWords(int mininumLength, int maximumLength) {
        ArrayList<String> limitedList = new ArrayList();

        for (String word : this.words) {
            if (word.length() >= mininumLength
                && word.length() <= maximumLength) {
                limitedList.add(word);
            }
        }

        return limitedList;
    }

    @Override
    public String getEntry(int index) throws DictionaryReaderException {
        String word = this.words.get(index);

        if (word == null) {
            throw new DictionaryReaderException();
        }

        return word;
    }

    /**
     * Initialize the XML file handler
     */
    protected void initXMLParser() throws XmlPullParserException {
        this.parser = Xml.newPullParser();
        this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        this.parser.setInput(this.inputStream, null);
    }

    /**
     * Parse the XML document and place the words in the list
     */
    protected void parseFile() throws IOException, XmlPullParserException {
        int eventType = this.parser.getEventType();
        String currentWord = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_TAG
                    && parser.getName().toLowerCase().equals(this.nodename.toLowerCase())) {
                currentWord = this.parser.nextText();

                // If the current word is not null, then store it in the list and clear the word
                if (currentWord != null) {
                    this.words.add(currentWord);
                    currentWord = null;
                }
            }

            eventType = this.parser.next();
        }
    }
}
