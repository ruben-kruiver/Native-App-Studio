package nl.mprog.apps.hangman11079592.model;

import android.graphics.Color;
import android.graphics.Paint;

import nl.mprog.apps.hangman11079592.basemodel.Figure;

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
     * Initialize the figure
     */
    public StickFigure() {
        super();

        this.current_stage = 0;
    }

    @Override
    /**
     * Draw the Figure on the canvas to give visual feedback
     */
    public void displayFigure() {
        // This method exceeds the maximum number of 20 lines of code
        // because the calculations depend on each other and each value is needed
        // to actually draw the figure

        float stroke_width = this.getSize(5);

        float top_margin = this.getSize(30);

        float radius_head = this.getSize(30);
        float head_center_x = (canvas.getWidth() / 2);
        float head_center_y = top_margin + radius_head; // Margin from the top

        float torso_left = canvas.getWidth() / 2;
        float torso_top = head_center_y + radius_head;
        float torso_right = torso_left;
        float torso_bottom = torso_top + this.getSize(100);

        float left_arm_left = torso_left - (canvas.getWidth() / 5);
        float left_arm_top = torso_top;
        float left_arm_right = torso_left + (stroke_width / 2);
        float left_arm_bottom = torso_top + this.getSize(50);

        float right_arm_left = left_arm_right - stroke_width;
        float right_arm_top = left_arm_bottom;
        float right_arm_right = torso_right + (canvas.getWidth() / 5);
        float right_arm_bottom = torso_top;

        float left_leg_left = torso_left - (canvas.getWidth() / 5);
        float left_leg_top = torso_bottom + getSize(50);
        float left_leg_right = torso_left;
        float left_leg_bottom = torso_bottom;

        float right_leg_left = torso_left;
        float right_leg_top = torso_bottom;
        float right_leg_right = torso_right + (canvas.getWidth() / 5);
        float right_leg_bottom = torso_bottom + getSize(50);

        Paint paintBlack = new Paint();
        paintBlack.setColor(Color.BLACK);
        paintBlack.setStrokeWidth(stroke_width);

        paintBlack.setAlpha(220);
        canvas.drawCircle(head_center_x, head_center_y, radius_head, paintBlack);
        canvas.drawLine(torso_left, torso_top, torso_right, torso_bottom, paintBlack);
        canvas.drawLine(left_arm_left, left_arm_top, left_arm_right, left_arm_bottom, paintBlack);
        canvas.drawLine(right_arm_left, right_arm_top, right_arm_right, right_arm_bottom, paintBlack);
        canvas.drawLine(left_leg_left, left_leg_top, left_leg_right, left_leg_bottom, paintBlack);
        canvas.drawLine(right_leg_left, right_leg_top, right_leg_right, right_leg_bottom, paintBlack);
    }

    @Override
    public void reset() {
        this.current_stage = 0;
    }

    @Override
    public void reset(int stage) {
        if (stage < 0) { stage = 0; }
        else if (stage > this.number_of_stages) { stage = this.number_of_stages; }

        this.current_stage = stage;
    }

    @Override
    public void nextStage() {
        this.current_stage++;
    }

    @Override
    public void setNumberOfStages(int number_of_stages) {
        if (number_of_stages < 1) {
            number_of_stages = 1;
        }

        this.number_of_stages = number_of_stages;
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
