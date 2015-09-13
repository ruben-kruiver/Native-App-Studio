package nl.mprog.apps.hangman11079592.readers;

import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.exceptions.DictionaryReaderException;
import nl.mprog.apps.hangman11079592.interfaces.DictionaryReader;

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
     * The root node of the document
     */
    protected String root;

    /**
     * The name of the word node
     */
    protected String nodename;

    /**
     * Initialize the reader with a file location
     * @param filepath
     */

    public XMLReader(String filepath) {
        super(filepath);
    }

    @Override
    public void loadFile() throws DictionaryReaderException {

    }

    @Override
    public ArrayList<String> getContent() throws DictionaryReaderException {
        return null;
    }

    @Override
    public ArrayList<String> getWords(int min, int max) {
        return null;
    }

    @Override
    public String getEntry(int index) throws DictionaryReaderException {
        return null;
    }

    /**
     * Set the node names
     *
     * @param root The name of the root of the XMLReader file
     * @param nodename The name of the node where the single words are stored
     */
    public void setNodeNames(String root, String nodename) {
        this.root = root;
        this.nodename = nodename;
    }
}
