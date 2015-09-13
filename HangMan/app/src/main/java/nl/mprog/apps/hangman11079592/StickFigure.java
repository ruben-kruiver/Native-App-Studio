package nl.mprog.apps.hangman11079592;

import android.view.View;

import nl.mprog.apps.hangman11079592.exceptions.FigureCompleteException;
import nl.mprog.apps.hangman11079592.interfaces.Figure;

/**
 * Build a StickFigure to be displayed to the user
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class StickFigure extends Figure {

    /**
     * The number of stages for this figure
     */
    protected int number_of_stages;

    /**
     * The current stage
     */
    protected int current_stage;

    /**
     * The view for this figure
     */
    protected View view;

    /**
     * Initialize the figure
     */
    public StickFigure() {
        this.number_of_stages = 7;
        this.current_stage = 0;
    }

    @Override
    /**
     * Get the generated figure based on the current stage
     * @return The View for displaying purposes
     */
    public View displayFigure() {
        /**
         * This methods needs to be implemented
         */
        return null;
    }

    @Override
    public void reset() {
        this.current_stage = 0;
    }

    @Override
    public void reset(int stage) throws FigureCompleteException {
        if (stage < 0) { stage = 0; }
        else if (stage > this.number_of_stages) { stage = this.number_of_stages; }

        this.current_stage = stage;

        if (this.isComplete()) { throw new FigureCompleteException(); }
    }

    @Override
    public void nextStage() throws FigureCompleteException {
        this.current_stage++;

        if (this.isComplete()) { throw new FigureCompleteException(); }
    }

    @Override
    public int getNumberOfStages() {
        return this.number_of_stages;
    }

    @Override
    public int getRemainingStages() {
        return this.getNumberOfStages() - this.current_stage;
    }

    @Override
    public int getCurrentStage() {
        return this.current_stage;
    }

    @Override
    public boolean isComplete() {
        return this.getRemainingStages() <= 0;
    }
}
