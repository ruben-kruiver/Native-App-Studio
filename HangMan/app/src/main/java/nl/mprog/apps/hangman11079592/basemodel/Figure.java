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
    protected int number_of_stages;

    /**
     * The number of the current stage
     */
    protected int current_stage;

    /**
     * The display metrics for conversion
     */
    protected DisplayMetrics display_metrics;

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
        this.display_metrics = metrics;
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
     * @param number_of_stages
     */
    public void setNumberOfStages(int number_of_stages) {
        this.number_of_stages = number_of_stages;
    }

    /**
     * Get the number of stages this figure will be built in
     * @return
     */
    public int getNumberOfStages() {
        return this.number_of_stages;
    }

    /**
     * Get the remaining number of stages
     * @return The number of stages remaining
     */
    public int getRemainingStages() {
        return this.number_of_stages - this.current_stage;
    }

    /**
     * Get the current stage
     * @return The number of the current stage
     */
    public int getCurrentStage() {
        return this.current_stage;
    }

    /**
     * Check if the figure is complete
     * @return Returns TRUE if the figure is complete, FALSE otherwise
     */
    public boolean isComplete() {
        return this.current_stage >= this.number_of_stages;
    }

    /**
     * Get the float size from a dp unit
     * @param dp_unit
     * @return the float size
     */
    public float getSize(int dp_unit) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp_unit, this.display_metrics);
    }
}
