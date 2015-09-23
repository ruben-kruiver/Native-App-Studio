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
 * The history contains a list of history entries that can be requested
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class History {

    protected ArrayList<Entry> entries;

    protected Entry currentEntry;

    protected String historyFilename;

    protected XmlPullParser resourceParser;

    protected String nodename = "entry"; // The name of the node to find the words

    public History(String historyFilename) {
        this.historyFilename = historyFilename;
        this.entries = new ArrayList();
        this.loadHistory();
    }

    /**
     * Start a new sequence of games that need to be registered
     *
     */
    public void startNewSequence() {
        // Don't restart a sequence when the previous is empty
        if (this.currentEntry != null
                && this.currentEntry.getScore() == 0) { return; }

        this.currentEntry = null;
        this.currentEntry = new Entry();
        this.entries.add(this.currentEntry);
    }

    public void newWordWon(String word) {
        if (this.currentEntry == null) {
            this.startNewSequence();
        }

        this.currentEntry.addWord(word);
    }

    public ArrayList<Entry> getHistory() {
        Collections.sort(this.entries);
        return this.entries;
    }

    public void storeHistory() {
        try {
            // Open the file
            FileOutputStream outputStream = new FileOutputStream(new File(this.historyFilename));

            // Initialize the XML serializer
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(outputStream, "UTF-8");
            xmlSerializer.startDocument(null, true);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            xmlSerializer.startTag(null, "entries");

            for (Entry entry : this.entries) {
                this.createNodeFromEntry(xmlSerializer, entry);
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
            // we cannot actually do something about it and neither can the user
            // Hopefully it will be saved in a next try, otherwise the history
            // will be lost when the application closes
        }
    }

    protected void loadHistory() {
        try {
            FileInputStream inputStream = new FileInputStream(new File(this.historyFilename));

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
     * This method creates a new entry from the current node in the resource parser
     * and adds it to the entries list
     */
    protected void createEntryFromNode() {
        Integer letters = Integer.parseInt(this.resourceParser.getAttributeValue(null, "total_letters"));
        Integer wordCount = Integer.parseInt(this.resourceParser.getAttributeValue(null, "total_words"));
        Long time = Long.parseLong(this.resourceParser.getAttributeValue(null, "time"));

        Entry entry = new Entry(letters, wordCount, time);
        this.entries.add(entry);
    }

    /**
     * This method creates a new DOM node and places it in the XML Serializer for storage
     */
    protected void createNodeFromEntry(XmlSerializer xmlSerializer, Entry entry) throws IOException {
        xmlSerializer.startTag(null, "entry");
        xmlSerializer.attribute(null, "total_letters", entry.getTotalLetters().toString());
        xmlSerializer.attribute(null, "total_words", entry.getTotalWordCount().toString());
        xmlSerializer.attribute(null, "time", entry.getTime().toString());
        xmlSerializer.endTag(null, "entry");
    }
}
