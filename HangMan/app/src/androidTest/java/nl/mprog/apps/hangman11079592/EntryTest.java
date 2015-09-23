package nl.mprog.apps.hangman11079592;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;

import nl.mprog.apps.hangman11079592.model.history.Entry;

public class EntryTest extends TestCase {

    public void setUp() {

    }

    public void testConstructWithAndWithoutArguments() {
        Entry entry_without = new Entry();
        this.assertEquals((Integer) 0, entry_without.getTotalWordCount());
        this.assertEquals((Integer) 0, entry_without.getTotalLetters());

        Entry entry_with = new Entry(1, 1, 1L);

        this.assertEquals((Integer) 1, entry_with.getTotalWordCount());
        this.assertEquals((Integer) 1, entry_with.getTotalLetters());
        this.assertEquals((Long) 1L, entry_with.getTime());
    }

    public void testAddWordIncreasesNumberOfWordsAndLetters() {
        String test = "test";

        Entry entry = new Entry();
        entry.addWord(test);
        this.assertEquals((Integer) 1, entry.getTotalWordCount());
        this.assertEquals((Integer) 4, entry.getTotalLetters());
    }

    public void testGetScoreReturnsTheExpectedScore() {
        String word_1 = "test";
        String word_2 = "this_is_a_longer_word";

        Entry entry = new Entry();
        entry.addWord(word_1);

        this.assertEquals((Integer) 4, entry.getScore());

        entry.addWord(word_2);

        this.assertEquals((Integer) 50, entry.getScore() );
    }

    public void testEntriesAreSortedByScoreInAscendingOrder() {
        ArrayList<Entry> entries = new ArrayList();

        Entry entry_1 = new Entry(1, 1, 1L);
        Entry entry_2 = new Entry(2, 1, 1L);
        Entry entry_3 = new Entry(20, 15, 1L);
        Entry entry_4 = new Entry(299, 1, 1L);

        entries.add(entry_1);
        entries.add(entry_2);
        entries.add(entry_3);
        entries.add(entry_4);

        Collections.sort(entries);

        // Expect the following sequence:
        // entry_3 (300 points), entry_4 (299 points), entry_2 (2 points), entry_1 (1 point)

        this.assertEquals((Integer) 20, entries.get(0).getTotalLetters());
        this.assertEquals((Integer) 299, entries.get(1).getTotalLetters());
        this.assertEquals((Integer) 2, entries.get(2).getTotalLetters());
        this.assertEquals((Integer) 1, entries.get(3).getTotalLetters());
    }
}
