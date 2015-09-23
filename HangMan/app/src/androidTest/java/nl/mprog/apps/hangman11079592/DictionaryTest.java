package nl.mprog.apps.hangman11079592;

import junit.framework.TestCase;

import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.model.Dictionary;

/**
 * Created by Ruben on 23-9-2015.
 */
public class DictionaryTest extends TestCase {

    public void testgetRandomWordFromDictionary() {
        ArrayList<String> newWords = new ArrayList();
        newWords.add("test1");
        newWords.add("test2");
        newWords.add("test3");
        newWords.add("test4");
        newWords.add("test5");

        Dictionary dictionary = new Dictionary(newWords);
        String word = dictionary.getRandomWord();

        // Assert the a word is actually returned
        this.assertNotNull(word);

        // Assert that the returned word exists in the given list
        this.assertTrue(newWords.contains(word));
    }
}
