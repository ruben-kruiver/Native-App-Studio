package nl.mprog.apps.hangman11079592.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains a list of words where the words can be selected from
 * and the user can play with
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class Dictionary {

    protected ArrayList<String> wordList;

    public Dictionary(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public String getRandomWord() {
       Random rand = new Random();
        int index = rand.nextInt(this.wordList.size());

        return this.wordList.get(index);
    }
}
