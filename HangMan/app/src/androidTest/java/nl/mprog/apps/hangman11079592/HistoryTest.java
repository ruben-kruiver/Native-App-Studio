package nl.mprog.apps.hangman11079592;

import android.app.Activity;

import junit.framework.TestCase;

import nl.mprog.apps.hangman11079592.model.History;

public class HistoryTest extends TestCase {
    protected History history;

    public void testSetUp() throws Exception {
        String historyFileName = "mock-history.xml";
        this.history = new History(historyFileName);
    }

    public void testNewSequenceAddsEntryToHistory() {
        // Assert that there are no entries in the history
        this.assertEquals(0, this.history.getHistory().size());

        this.history.startNewSequence();

        // Assert that there is 1 entry added in the history
        this.assertEquals(1, this.history.getHistory().size());

        this.history.startNewSequence();

        // Assert that again a new entry is created
        this.assertEquals(2, this.history.getHistory().size());
    }

    public void testAddNewWordWithoutCurrentEntryAddsNewEntryToHistory() {
        this.assertEquals(0, this.history.getHistory().size());

        String test = "test";
        this.history.newWordWon(test);

        this.assertEquals(1, this.history.getHistory().size());
    }
}