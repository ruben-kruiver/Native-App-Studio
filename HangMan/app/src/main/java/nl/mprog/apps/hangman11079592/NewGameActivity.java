package nl.mprog.apps.hangman11079592;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import nl.mprog.apps.hangman11079592.enumerations.Level;
import nl.mprog.apps.hangman11079592.exceptions.DuplicateAttemptException;
import nl.mprog.apps.hangman11079592.exceptions.GameOverException;
import nl.mprog.apps.hangman11079592.exceptions.GameWonException;
import nl.mprog.apps.hangman11079592.interfaces.DictionaryReader;
import nl.mprog.apps.hangman11079592.readers.CSVReader;

public class NewGameActivity extends Activity implements SurfaceHolder.Callback {
    /**
     * The word that will be displayed on the screen
     * for the user to play
      */
    protected TextView displayWord;

    /**
     * The feedback to the user based on the last actions
     */
    protected TextView feedbackMessage;

    /**
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        this.initFigureSurface();

        if (this.displayWord == null) {
            this.displayWord = (TextView) this.findViewById(R.id.displayWord);
            this.displayWord.setText("Dit is een tekst");
        }

        if (this.feedbackMessage == null) {
            this.feedbackMessage = (TextView) this.findViewById(R.id.feedbackMessage);
        }

        this.startGame();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.tryDrawing(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.tryDrawing(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void enterChar(View view) {
        String id = view.getResources().getResourceEntryName(view.getId());
        String guess = id.substring(4); // Strip of the "key_" prefix

        try {
            this.guessChar(guess);
        } catch (GameOverException | GameWonException e) {

        }
    }

    protected void initFigureSurface() {
        SurfaceView figureView = (SurfaceView)findViewById(R.id.figureView);
        figureView.setZOrderOnTop(true);    // necessary
        SurfaceHolder figureViewHolder = figureView.getHolder();
        figureViewHolder.setFormat(PixelFormat.TRANSPARENT);

        figureViewHolder.addCallback(this);
    }

    protected void tryDrawing(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    protected void drawMyStuff(final Canvas canvas) {

    }

    protected void startGame() {
        HangMan hangMan = HangMan.getInstance();
        hangMan.setDifficulty(Level.EASY);

        DictionaryReader reader = new CSVReader("mijnbestand.txt");
        hangMan.setReader(reader);

        hangMan.loadGame("moeilijk");
    }

    protected void guessChar(String guess) throws GameOverException, GameWonException {
        HangMan hangMan = HangMan.getInstance();
        try {
            if (guess.length() == 0) { throw new IllegalArgumentException(); }

            String feedback = this.createFeedbackMessage(hangMan.enterChar(guess.charAt(0)));

            this.feedbackMessage.setText(feedback);
        } catch (IllegalArgumentException e) {
            this.feedbackMessage.setText("U moet een waarde ingeven");
        } catch (DuplicateAttemptException e) {
            this.feedbackMessage.setText("U heeft deze letter al een keer gebruikt");
        } catch (GameOverException e) {
            this.feedbackMessage.setText("U heeft verloren!");
            throw e;
        } catch (GameWonException e) {
            this.feedbackMessage.setText("U heeft gewonnen!");
            throw e;
        } finally {
            this.displayWord.setText(hangMan.getGameState());
        }
    }

    protected String createFeedbackMessage(boolean correct) {
        String feedback = "";

        if (correct) { feedback = "Goed geraden!\n"; }
        else { feedback = "Helaas, dat was niet goed.\n"; }

        feedback += String.format("U heeft nog %d poging(en) over", HangMan.getInstance().getCurrentFigure().getRemainingStages());

        return feedback;
    }
}
