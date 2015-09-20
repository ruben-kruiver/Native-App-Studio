package nl.mprog.apps.hangman11079592.model;

/**
 * This class is primarily responsible for setting global constant values
 * and as a container for globally used models.
 *
 */
public class HangMan {
    /**
     * The global constants
     */
    public static final int NEW_GAME = -1;
    public static final int GAME_WON = 0;
    public static final int GAME_LOST = 2;
    public static final int GUESS_OK = 3;
    public static final int GUESS_FAIL = 4;
    public static final int DUPLICATE_ATTEMPT = 5;

    public static final int DEFAULT_WORD_LENGTH = 7;
    public static final int DEFAULT_CHANCES = 7;

    protected static History historyInstance;

    /**
     * Set the history instance for HangMan
     * @param historyInstance
     */
    public static void setHistory(History historyInstance) {
        HangMan.historyInstance = historyInstance;
    }

    /**
     * Get the history instance for HangMan
     * @return The history instance
     */
    public static History getHistoryInstance() {
        return HangMan.historyInstance;
    }
}
