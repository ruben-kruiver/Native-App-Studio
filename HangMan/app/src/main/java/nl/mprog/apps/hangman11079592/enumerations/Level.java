package nl.mprog.apps.hangman11079592.enumerations;

/**
 * The enumeration holds the levels of difficulty
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public enum Level {
    EASY("Easy", 3, 5),
    INTERMEDIATE("Intermediate", 6, 7),
    DIFFUCULT("Difficult", 8, 15),
    HARD("Hard", 16, 40);

    /**
     * The real world representation of the current level
     */
    private String description;

    /**
     * The min and max ranges for this level
     */
    private int min, max;

    /**
     * Set the current Level
     *
     * @param description The real word description
     * @param min The bottom range
     * @param max The upper range
     */
    private Level(String description, int min, int max) {
        this.description = description;
    }

    /**
     * Get the bottom range for this level
     * @return
     */
    public int getMin(){
        return this.min;
    }

    /**
     * Get the upper range for this level
     * @return
     */
    public int getMax() {
        return this.max;
    }

    /**
     * Get the description for this level
     * @return
     */
    public String getDescription() {
        return this.description;
    }
};

