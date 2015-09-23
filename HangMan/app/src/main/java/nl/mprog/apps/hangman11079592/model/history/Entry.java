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

    protected int totalLetters;

    protected int totalCorrectWords;

    protected Date date;

    protected Long lastTime;

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

    public void addWord(String word) {
        this.totalCorrectWords++;
        this.totalLetters += word.length();
        this.lastTime = this.date.getTime();
    }

    /**
     * The score is the multiplication of the total correct words and the total letters of the words
     * @return
     */
    public Integer getScore() {
        return this.totalCorrectWords * this.totalLetters;
    }

    public Long getTime() {
        return this.lastTime;
    }

    public Integer getTotalWordCount() {
        return this.totalCorrectWords;
    }

    public Integer getTotalLetters() {
        return this.totalLetters;
    }

    /**
     * Allow for sorting in ascending order based on the score for the entry
     *
     * @param entry The entry to compare to
     * @return
     */
    @Override
    public int compareTo(Entry entry) {
        return entry.getScore() - this.getScore();
    }
}
