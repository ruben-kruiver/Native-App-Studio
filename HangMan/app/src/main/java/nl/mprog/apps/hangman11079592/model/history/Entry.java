package nl.mprog.apps.hangman11079592.model.history;

import java.io.Serializable;
import java.util.Date;

/**
 * This class contains the values for each complete sequential won games, including
 * a current running game.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Entry implements Serializable, Comparable<Entry> {
    /**
     * The combined number of letters of correct guessed words
     */
    protected int total_letters;

    /**
     * The number of sequential words guessed
     */
    protected int total_correct_words;

    /**
     * The date object to request the current date/time
     */
    protected Date date;

    /**
     * The time in milliseconds since Epoch of the last won game
     */
    protected Long last_time;

    /**
     * Initialize a new entry
     */
    public Entry() {
        this.date = new Date();
        this.total_correct_words = 0;
        this.total_letters = 0;
        this.last_time = this.date.getTime();
    }

    /**
     * Allow for the entry to reinitialize with previous values
     *
     * @param total_letters The total letters of the entry
     * @param total_correct_words The total number of correct words of the entry
     */
    public Entry(int total_letters, int total_correct_words, Long time) {
        this();
        this.total_letters = total_letters;
        this.total_correct_words = total_correct_words;
        this.last_time = time;
    }

    /**
     * Add a new word to the list of won games
     *
     * @param word
     */
    public void addWord(String word) {
        this.total_correct_words++;
        this.total_letters += word.length();
        this.last_time = this.date.getTime();
    }

    /**
     * Get the score for this entry
     */
    public Integer getScore() {
        return this.total_correct_words * this.total_letters;
    }

    /**
     * Get the time in milliseconds for the last won game
     */
    public Long getTime() {
        return this.last_time;
    }

    /**
     * Get the total number of words
     */
    public Integer getTotalWordCount() {
        return this.total_correct_words;
    }

    /**
     * Get the total number of letters in all words
     */
    public Integer getTotalLetters() {
        return this.total_letters;
    }

    /**
     * Allow for sorting in ascending order based on the score for the entry
     * @param entry The entry to compare to
     * @return
     */
    @Override
    public int compareTo(Entry entry) {
        return entry.getScore() - this.getScore();
    }
}
