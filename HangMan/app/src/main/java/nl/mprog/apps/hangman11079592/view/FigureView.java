package nl.mprog.apps.hangman11079592.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nl.mprog.apps.hangman11079592.basemodel.Figure;

/**
 * Created by Ruben on 16-9-2015.
 */
public class FigureView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * The figure instance for this view
     */
    protected Figure figure;

    /**
     * Initialize with context
     * @param context
     */
    public FigureView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    /**
     * Initialize with context and attribute set
     * @param context
     * @param attributeSet
     */
    public FigureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /**
     * Initialize with context, attribute set and default style attribute
     * @param context
     * @param attributeSet
     * @param defaultStyleAttribute
     */
    public FigureView(Context context, AttributeSet attributeSet, int defaultStyleAttribute) {
        super(context, attributeSet, defaultStyleAttribute);
    }

    /**
     * Set the figure for this view
     * @param figure
     */
    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
