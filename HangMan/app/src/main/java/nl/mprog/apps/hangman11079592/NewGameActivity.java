package nl.mprog.apps.hangman11079592;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nl.mprog.apps.hangman11079592.basemodel.Figure;
import nl.mprog.apps.hangman11079592.basemodel.HangManActivity;
import nl.mprog.apps.hangman11079592.model.Dictionary;
import nl.mprog.apps.hangman11079592.model.Gameplay;
import nl.mprog.apps.hangman11079592.model.HangMan;
import nl.mprog.apps.hangman11079592.model.History;
import nl.mprog.apps.hangman11079592.model.StickFigure;
import nl.mprog.apps.hangman11079592.model.reader.XMLReader;

/**
 * This class handles the game flow of the Gameplay. It is the first entry point for
 * the player when he starts the application and it will handle all initial setup
 * in order for the player to commence playing
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */

public class NewGameActivity extends HangManActivity implements SurfaceHolder.Callback {

    protected TextView displayWord; // This attribute contains the string that will
                                    // be displayed to the screen for the user to guess
                                    // Unknown characters are replaced by underscores

    protected TextView feedbackMessage;

    protected Figure figure;

    protected Gameplay gameplay;

    protected SharedPreferences preferences;

    protected String dictionaryFile;

    protected SurfaceHolder surfaceHolder;

    protected History history;

    protected boolean gameComplete = true;

    protected boolean resetWordLength = false;

    protected boolean resetFigureStages = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = this.getIntent();

        switch (id) {
            case R.id.action_settings :
                // Set a flag to reset the word length when the user returns from
                // the settings activity. The callback will fail because
                // this activity runs as single instance to reduce memory usage.
                this.resetWordLength = true;

                intent.setClass(this, SettingsActivity.class);
                intent.putExtra("maxWordLength", XMLReader.getLongestWordLength());
                this.startActivity(intent);
                break;

            case R.id.action_highscores:
                intent.setClass(this, HighScoreActivity.class);
                this.startActivity(intent);
                break;

            case R.id.action_new_game:
                this.startGame();
                break;

            case R.id.action_quit:
                this.finish();
                System.exit(0);
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        this.drawFigure();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // No actions needed. The figure will be drawn with each guess
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The holder is stored so that the figure can always draw to the canvas
        // We need to remove the reference here
        this.surfaceHolder = null;
    }

    public void newGame(View view) {
        this.startGame();
    }

    /**
     * This method handles the guessed character by the player. After that
     * it will display the toggled button to a dark gray state to give
     * visual confirmation which letters are already tried
     *
     * @param view
     */
    public void enterChar(View view) {
        // Disable key logging when the current game is complete
        if (this.gameComplete) {
            return;
        }

        Button button = (Button) view;
        String guess = (String) button.getText();
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.DKGRAY);

        this.guessChar(guess);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_game);

        this.setPrimaryClassAttributes();

        this.loadHistory();

        this.initFigure();

        this.loadGameplay();

        this.startGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // When the user retursn from the settings activity
        // reset the values for the game
        if (this.resetWordLength) {
            this.gameplay.setDictionary(this.loadDictionary());

            // The figure needs to be reset when
            // the new game starts, not sooner
            this.resetFigureStages = true;

            this.resetWordLength = false;
        }
    }

    /**
     * This method initializes the primary class attributes
     * when this class is first called through the onCreate method
     */
    protected void setPrimaryClassAttributes() {
        this.dictionaryFile = this.getString(R.string.dictionary_filename);
        this.preferences = this.getSharedPreferences(this.getString(R.string.preferences_name),
                Context.MODE_PRIVATE);

        this.displayWord = (TextView) this.findViewById(R.id.displayWord);
        this.feedbackMessage = (TextView) this.findViewById(R.id.feedbackMessage);
    }

    protected void loadGameplay() {
        if (this.gameplay == null) {
            this.gameplay = new Gameplay();

            // Load the dictionary for the game
            this.gameplay.setDictionary(this.loadDictionary());

            // Set the figure for the current gameplay
            this.gameplay.setFigure(this.figure);
        }
    }

    /**
     * This method initializes the Figure instance
     * that will display the figure on the SurfaceView
     */
    protected void initFigure() {
        // Load the maximum allowed guesses from the preferences
        Integer maximum_guesses_allowed = this.preferences.getInt("guesses_allowed", 7);

        // Load the figure instance
        this.figure = new StickFigure();
        this.figure.setDisplayMetrics(this.getResources().getDisplayMetrics());
        this.figure.setNumberOfStages(maximum_guesses_allowed);

        // Load the figureview from the layout
        SurfaceView figureView = (SurfaceView)this.findViewById(R.id.figureView);
        figureView.setZOrderOnTop(true);    // necessary

        // Load the viewholder for this view
        SurfaceHolder figureViewHolder = figureView.getHolder();
        figureViewHolder.setFormat(PixelFormat.TRANSPARENT);
        figureViewHolder.addCallback(this);

        // Set the number of stages equal to the number of chances
        Integer chances = this.preferences.getInt("chances", HangMan.DEFAULT_CHANCES);
        this.figure.setNumberOfStages(chances);
    }

    /**
     * This method will load the dictionary from the dictionary file. If it fails
     * to do so, it will set the feedback message to inform the user and loads
     * an empty dictionary.
     */
    protected Dictionary loadDictionary() {
        Dictionary dictionary;
        try {
            InputStream inputStream = this.getApplicationContext().getAssets().open(this.dictionaryFile);
            Integer maximumLength = this.preferences.getInt("wordLength", HangMan.DEFAULT_WORD_LENGTH);
            dictionary = XMLReader.loadDictionary(inputStream, 1, maximumLength);
            return dictionary;
        } catch (IOException | XmlPullParserException ex) {
            // We catch the exception and display an error to the user
            // Additionally we create and empty Dictionary to prevent
            // null pointer exceptions

            this.feedbackMessage.setText(R.string.error_dictionary_io_exception);
            dictionary = new Dictionary(new ArrayList<String>());
        }

        return dictionary;
    }

    protected void loadHistory() {
        String filename = this.getFilesDir() + this.getString(R.string.history_filename);
        this.history = new History(filename);

        // Make the history file available throughout the application
        HangMan.setHistory(this.history);
    }

    /**
     * This method will start a new game and will reset the figure
     * and the word and feedback message
     */
    protected void startGame() {
        if (this.resetFigureStages) {
            Integer chances = this.preferences.getInt("chances", HangMan.DEFAULT_CHANCES);
            this.figure.setNumberOfStages(chances);
            this.resetFigureStages = false;
        }

        this.gameplay.startGame();

        // Set the initial message for the feedback
        this.setFeedbackMessage(HangMan.NEW_GAME);
        this.feedbackMessage.setText(this.getResources().getString(R.string.gameplay_new_game));

        // Reset the buttons to the original color
        // because they might be displayed in dark gray
        // from an earlier game
        this.resetKeyboardButtons();

        // Make sure the color of the text is black
        // this could be changed in a previous game
        this.displayWord.setTextColor(Color.BLACK);

        this.displayWord.setText(this.gameplay.getDisplayWord());

        // (Re-)enable the game
        this.gameComplete = false;
    }

    /**
     * Enter the guessed character and validate it's outcome. It also sends
     * a signal to the figure that it needs to draw itself on the surface
     *
     * @param guess
     */
    protected void guessChar(String guess)  {
        int result = this.gameplay.enterChar(guess.charAt(0));

        this.setFeedbackMessage(result);

        if (result == HangMan.GAME_LOST) {
            // Display the lost word in red
            this.displayWord.setTextColor(Color.RED);
            this.gameComplete = true;
        } else if (result == HangMan.GAME_WON) {
            // Display the won word in green
            this.displayWord.setTextColor(Color.GREEN);
            this.gameComplete = true;

            this.transitionToHighscores();
        }

        this.displayWord.setText(this.gameplay.getDisplayWord());
        this.drawFigure();
    }

    /**
     * Create and display the feedback message to the user based
     * on the last game state.
     *
     * @param gamestate
     */
    protected void setFeedbackMessage(int gamestate) {
        String chances_remaining = String.format(this.getResources().getString(R.string.gameplay_chances_remaining),
                this.figure.getRemainingStages());

        switch (gamestate) {
            case HangMan.GUESS_OK:
                this.feedbackMessage.setText(this.getResources().getString(R.string.gameplay_correct_guess)
                        + chances_remaining);
                break;

            case HangMan.GUESS_FAIL:
                this.feedbackMessage.setText(this.getResources().getString(R.string.gameplay_incorrect_guess)
                        + chances_remaining);
                break;

            case HangMan.DUPLICATE_ATTEMPT:
                this.feedbackMessage.setText(this.getResources().getString(R.string.gameplay_duplicate_character)
                        + chances_remaining);
                break;

            case HangMan.GAME_WON:
                this.feedbackMessage.setText(R.string.gameplay_won);
                break;

            case HangMan.GAME_LOST:
                this.feedbackMessage.setText(R.string.gameplay_lost);
                break;

            default :
                this.feedbackMessage.setText(R.string.gameplay_new_game);
        }
    }

    protected void drawFigure() {
        if (this.surfaceHolder == null) {
            return;
        }

        Canvas canvas = this.surfaceHolder.lockCanvas();
        this.figure.setCanvas(canvas);
        this.figure.displayFigure();
        this.surfaceHolder.unlockCanvasAndPost(canvas);
    }

    /**
     * Reset all the buttons to the original color and state
     */
    protected void resetKeyboardButtons() {
        String letters[] = {"a","b","c","d","e","f","g","h","i",
                            "j","k","l","m","n","o","p","q","r",
                            "s","t","u","v","w","x","y","z"} ;

        for (String letter : letters) {
            try {
                Button charButton = (Button) this.findViewByStringId("key_" + letter);
                charButton.setTextColor(Color.BLACK);
                charButton.setBackgroundColor(Color.LTGRAY);
            } catch (Exception ex) {}
        }
    }

    /**
     * Let the player transition to the highscores screen
     * to view the results
     */
    protected void transitionToHighscores() {
        Intent intent = new Intent();
        intent.setClass(this, HighScoreActivity.class);
        this.startActivity(intent);
    }
}
