package nl.mprog.apps.hangman11079592.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import nl.mprog.apps.hangman11079592.model.history.Entry;

/**
 * This class is responsible for handling the history of the game.
 * The history contains a list of dates, times and it value of sequential
 * won games times the letters used during each word. Those two values multiplied
 * form the value of the score.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class History {

    /**
     * The list of history entries
     */
    protected ArrayList<Entry> entries;

    /**
     * The current entry in the sequence of games
     */
    protected Entry current_entry;

    /**
     * The context where the history was created
     */
    protected String filename;

    /**
     * The location of the history file
     */
    protected XmlPullParser resourceParser;

    /**
     * The name of the entry node
     */
    protected String nodename = "entry";

    /**
     * Initialize the history
     * @param filename The context activity for this history item
     */
    public History(String filename) {
        this.filename = filename;
        this.entries = new ArrayList();
    }

    /**
     * Start a new sequence of games that need to be registered
     *
     */
    public void startNewSequence() {
        // Don't restart a sequence when the previous is empty
        if (this.current_entry != null
                && this.current_entry.getScore() == 0) { return; }

        this.current_entry = null;
        this.current_entry = new Entry();
        this.entries.add(this.current_entry);
    }

    /**
     * Add a new word to the current entry
     */
    public void newWordWon(String word) {
        if (this.current_entry == null) {
            this.startNewSequence();
        }

        this.current_entry.addWord(word);
    }

    /**
     * Get the list of entries in order of total score
     */
    public ArrayList<Entry> getHistory() {
        Collections.sort(this.entries);
        return this.entries;
    }

    /**
     * Save the list to a file
     */
    public void storeHistory() {
        try {
            // Open the file
            FileOutputStream outputStream = new FileOutputStream(new File(this.filename));

            // Initialize the XML serializer
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(outputStream, "UTF-8");
            xmlSerializer.startDocument(null, true);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            xmlSerializer.startTag(null, "entries");

            for (Entry entry : this.entries) {
                this.addEntryToNode(xmlSerializer, entry);
            }

            xmlSerializer.endTag(null, "entries");

            // Finish the document
            xmlSerializer.endDocument();

            // Save the file to the disc
            xmlSerializer.flush();

            // Close the output stream
            outputStream.close();

        } catch (IOException ex) {
            // For some reason the file could not be created or written
        }
    }

    /**
     * Load the history from the file
     */
    protected void loadHistory() {
        try {
            FileInputStream inputStream = new FileInputStream(new File(this.filename));

            XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
            pullParserFactory.setNamespaceAware(false);

            this.resourceParser = pullParserFactory.newPullParser();
            this.resourceParser.setInput(inputStream, null);

            String tagName;
            int eventType = this.resourceParser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                tagName = this.resourceParser.getName();

                if (eventType == XmlPullParser.START_TAG
                        && tagName.equalsIgnoreCase(this.nodename)) {
                    this.createEntryFromNode();
                }

                eventType = this.resourceParser.next();
            }
        } catch (XmlPullParserException | IOException ex) {
            // Either the file doesn't exists or is not readable
            // We don't need to act on it, because the container is
            // already created and entries can be added
            // Upon save a new file will be created.
        }
    }

    /**
     * Create a new entry from the current node
     */
    protected void createEntryFromNode() {
        Integer letters = Integer.parseInt(this.resourceParser.getAttributeValue(null, "total_letters"));
        Integer wordCount = Integer.parseInt(this.resourceParser.getAttributeValue(null, "total_words"));
        Long time = Long.parseLong(this.resourceParser.getAttributeValue(null, "time"));

        Entry entry = new Entry(letters, wordCount, time);
        this.entries.add(entry);
    }

    /**
     * Add a new node to the DOM tree
     */
    protected void addEntryToNode(XmlSerializer xmlSerializer, Entry entry) throws IOException {
        xmlSerializer.startTag(null, "entry");
        xmlSerializer.attribute(null, "total_letters", entry.getTotalLetters().toString());
        xmlSerializer.attribute(null, "total_words", entry.getTotalWordCount().toString());
        xmlSerializer.attribute(null, "time", entry.getTime().toString());
        xmlSerializer.endTag(null, "entry");
    }
}
