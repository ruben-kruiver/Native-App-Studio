package nl.mprog.apps.hangman11079592.model.history;

import java.util.Date;

/**
 * This class contains the values for each complete sequential won games, including
 * a current running game.
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Entry implements Comparable<Entry> {
    /**
     * The combined number of letters of correct guessed words
     */
    protected int totalLetters;

    /**
     * The number of sequential words guessed
     */
    protected int totalCorrectWords;

    /**
     * The date object to request the current date/time
     */
    protected Date date;

    /**
     * The time in milliseconds since Epoch of the last won game
     */
    protected Long lastTime;

    /**
     * Initialize a new entry
     */
    public Entry() {
        this.date = new Date();
        this.totalCorrectWords = 0;
        this.totalLetters = 0;
        this.lastTime = this.date.getTime();
    }

    /**
     * Allow for the entry to reinitialize with previous values
     *
     * @param total_letters The total letters of the entry
     * @param total_correct_words The total number of correct words of the entry
     */
    public Entry(int total_letters, int total_correct_words, Long time) {
        this();
        this.totalLetters = total_letters;
        this.totalCorrectWords = total_correct_words;
        this.lastTime = time;
    }

    /**
     * Add a new word to the list of won games
     *
     * @param word
     */
    public void addWord(String word) {
        this.totalCorrectWords++;
        this.totalLetters += word.length();
        this.lastTime = this.date.getTime();
    }

    /**
     * Get the score for this entry
     */
    public Integer getScore() {
        return this.totalCorrectWords * this.totalLetters;
    }

    /**
     * Get the time in milliseconds for the last won game
     */
    public Long getTime() {
        return this.lastTime;
    }

    /**
     * Get the total number of words
     */
    public Integer getTotalWordCount() {
        return this.totalCorrectWords;
    }

    /**
     * Get the total number of letters in all words
     */
    public Integer getTotalLetters() {
        return this.totalLetters;
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
