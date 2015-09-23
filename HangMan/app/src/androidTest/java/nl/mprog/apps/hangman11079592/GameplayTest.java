package nl.mprog.apps.hangman11079592;

import junit.framework.TestCase;

import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.basemodel.Figure;
import nl.mprog.apps.hangman11079592.model.Dictionary;
import nl.mprog.apps.hangman11079592.model.Gameplay;
import nl.mprog.apps.hangman11079592.model.HangMan;
import nl.mprog.apps.hangman11079592.model.History;
import nl.mprog.apps.hangman11079592.model.StickFigure;

public class GameplayTest extends TestCase {

    protected Gameplay gameplay;

    protected Figure figure;

    public void setUp() {
        this.figure = new StickFigure();

        // Initialize the dictionary
        ArrayList<String> words = this.getWordList();
        Dictionary dictionary = new Dictionary(words);

        // Initialize the history instance and store it with the HangMan holder
        History history = new History("mock-history.xml");
        HangMan.setHistory(history);

        this.gameplay = new Gameplay();
    }

    public void testDisplayWordReturnsUnderscoreCharacterString() {
        this.gameplay.startGame();

        this.assertEquals("_ _ _ _ _", this.gameplay.getDisplayWord());
    }

    public void testEnterCharRevealsTheLetterOnTheExpectedPosition() {
        this.gameplay.startGame();

        this.gameplay.enterChar('t');

        this.assertEquals("t _ _ t _", this.gameplay.getDisplayWord());

        this.gameplay.enterChar('1');

        this.assertEquals("t _ _ t 1", this.gameplay.getDisplayWord());

        this.gameplay.enterChar('s');

        this.assertEquals("t _ s t 1", this.gameplay.getDisplayWord());
    }

    public void testEnterCharReturnsDuplicateResultOnSecondAttempt() {
        this.gameplay.startGame();

        this.gameplay.enterChar('t');

        this.assertEquals("t _ _ t _", this.gameplay.getDisplayWord());

        this.assertEquals(HangMan.DUPLICATE_ATTEMPT, this.gameplay.enterChar('t'));
    }

    public void testEnterCharReturnsFailedOnWrongCharacter() {
        this.gameplay.startGame();

        this.assertEquals(HangMan.GUESS_FAIL, this.gameplay.enterChar('x'));
    }

    public void testEnterCharReturnsGameLostWhenFigureComplete() {
        this.figure.setNumberOfStages(1);

        this.assertEquals(HangMan.GAME_LOST, this.gameplay.enterChar('x'));
    }

    public void testEnterCharRetunsGameWonWhenAllCharGuessed() {
        this.gameplay.startGame();
        this.gameplay.enterChar('t');
        this.gameplay.enterChar('e');
        this.gameplay.enterChar('s');

        this.assertEquals(HangMan.GAME_WON, this.gameplay.enterChar('1'));
    }

    /**
     * Create a list of fake words for the gameplay test setup
     *
     */
    protected ArrayList<String> getWordList() {
        ArrayList<String> words = new ArrayList();
        words.add("test1");
        return words;
    }
}
