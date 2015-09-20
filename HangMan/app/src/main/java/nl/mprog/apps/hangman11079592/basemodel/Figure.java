package nl.mprog.apps.hangman11079592.basemodel;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * An abstract base figure for the Gameplay game
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public abstract class Figure {

    /**
     * The canvas where the figure instance can draw on
     */
    protected Canvas canvas;

    /**
     * The number of stages to complete this figure
     */
    protected int numberOfStages;

    /**
     * The number of the current stage
     */
    protected int currentStage;

    /**
     * The display metrics for conversion
     */
    protected DisplayMetrics displayMetrics;

    /**
     * Set the canvas for this figure
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Set the display metrics for screen conversion
     */
    public void setDisplayMetrics(DisplayMetrics metrics) {
        this.displayMetrics = metrics;
    }

    /**
     * Draw the Figure on the canvas to give visual feedback
     */
    public abstract void displayFigure();

    /**
     * Reset the figure to it's base values
     */
    public abstract void reset();

    /**
     * Reset the figure to a specific stage
     * @param stage The stage
     */
    public abstract void reset(int stage);

    /**
     * Increment the stage
     */
    public abstract void nextStage();

    /**
     * Set the total number of stages for this figure
     * @param numberOfStages
     */
    public void setNumberOfStages(int numberOfStages) {
        this.numberOfStages = numberOfStages;
    }

    /**
     * Get the number of stages this figure will be built in
     * @return
     */
    public int getNumberOfStages() {
        return this.numberOfStages;
    }

    /**
     * Get the remaining number of stages
     * @return The number of stages remaining
     */
    public int getRemainingStages() {
        return this.numberOfStages - this.currentStage;
    }

    /**
     * Get the current stage
     * @return The number of the current stage
     */
    public int getCurrentStage() {
        return this.currentStage;
    }

    /**
     * Check if the figure is complete
     * @return Returns TRUE if the figure is complete, FALSE otherwise
     */
    public boolean isComplete() {
        return this.currentStage >= this.numberOfStages;
    }

    /**
     * Get the float size from a dp unit
     * @param dp_unit
     * @return the float size
     */
    public float getSize(int dp_unit) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp_unit, this.displayMetrics);
    }
}
