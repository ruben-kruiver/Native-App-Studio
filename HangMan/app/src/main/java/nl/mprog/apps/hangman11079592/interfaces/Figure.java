package nl.mprog.apps.hangman11079592.interfaces;

import android.view.View;

import nl.mprog.apps.hangman11079592.exceptions.FigureCompleteException;

/**
 * An abstract base figure for the HangMan game
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public abstract class Figure {

    /**
     * Generate a View that can be used for displaying the figure
     */
    public abstract View displayFigure();

    /**
     * Reset the figure to it's base values
     */
    public abstract void reset();

    /**
     * Reset the figure to a specific stage
     * @param stage The stage
     */
    public abstract void reset(int stage) throws FigureCompleteException;

    /**
     * Increment the stage
     */
    public abstract void nextStage() throws FigureCompleteException;

    /**
     * Get the number of stages this figure will be built in
     * @return
     */
    public abstract int getNumberOfStages();

    /**
     * Get the remaining number of stages
     * @return The number of stages remaining
     */
    public abstract int getRemainingStages();

    /**
     * Get the current stage
     * @return The number of the current stage
     */
    public abstract int getCurrentStage();

    /**
     * Check if the figure is complete
     * @return Returns TRUE if the figure is complete, FALSE otherwise
     */
    public abstract boolean isComplete();
}
