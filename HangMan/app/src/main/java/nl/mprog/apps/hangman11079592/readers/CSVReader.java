package nl.mprog.apps.hangman11079592.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import nl.mprog.apps.hangman11079592.exceptions.DictionaryReaderException;
import nl.mprog.apps.hangman11079592.interfaces.DictionaryReader;

/**
 * Load a dictionary from a CSVReader file source. The loaded CSVReader file should
 * contain a single column. If more columns are in a line, the first
 * column will be interpreted to contain the words. The other columns
 * will be ignored.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class CSVReader extends DictionaryReader {
    /**
     * The seperator for the file
     */
    protected String seperator;

    /**
     * The io reader for the file
     */
    protected BufferedReader reader;

    /**
     * Flag set when the file has been loaded succesfully
     */
    protected boolean isLoaded;

    /**
     * Initialize the reader with a file location
     * @param filepath
     */

    public CSVReader(String filepath) {
        super(filepath);

        this.seperator = ",";
        this.isLoaded = false;
    }

    /**
     * Load the CSVReader file
     */
    public void loadFile() throws DictionaryReaderException {
        String line;

        // Don't reload the file
        if (this.isLoaded) { return; }

        try {
            this.reader = new BufferedReader(new FileReader(this.filepath));

            while ((line = this.reader.readLine()) != null) {
                String[] linevalues = line.split(this.seperator);
                if (linevalues.length == 0) { continue; }
                this.words.add(linevalues[0]);
            }

            this.isLoaded = true;
        } catch(FileNotFoundException e) {
            throw new DictionaryReaderException();
        } catch(IOException e) {
            throw new DictionaryReaderException();
        } finally {
            if (this.reader != null) {
                try {
                    this.reader.close();
                } catch (IOException e) {
                    throw new DictionaryReaderException();
                }
            }
        }
    }

    /**
     * Get the list of words with a specific length range
     *
     * @param min The minimum word length
     * @param max The maximum word length
     */
    public ArrayList<String> getWords(int min, int max){
        ArrayList<String> temp = new ArrayList();

        for (String word : this.words) {
            if (word.length() >= min && word.length() <= max) {
                temp.add(word);
            }
        }

        return temp;
    }

    /**
     * Set the seperator for this file
     */
    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }

    /**
     * Get the list of found words
     * @return
     */
    @Override
    public ArrayList<String> getContent() {
        return this.words;
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public String getEntry(int index) {
        return null;
    }
}
