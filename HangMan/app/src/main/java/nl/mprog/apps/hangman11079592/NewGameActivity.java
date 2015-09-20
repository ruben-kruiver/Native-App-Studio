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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import nl.mprog.apps.hangman11079592.basemodel.Figure;;
import nl.mprog.apps.hangman11079592.basemodel.HangManActivity;
import nl.mprog.apps.hangman11079592.exception.DictionaryReaderException;
import nl.mprog.apps.hangman11079592.basemodel.DictionaryReader;
import nl.mprog.apps.hangman11079592.model.Gameplay;
import nl.mprog.apps.hangman11079592.model.HangMan;
import nl.mprog.apps.hangman11079592.model.History;
import nl.mprog.apps.hangman11079592.model.StickFigure;
import nl.mprog.apps.hangman11079592.model.reader.XMLReader;
import nl.mprog.apps.hangman11079592.view.FigureView;

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
    /**
     * The word that will be displayed on the screen
     * for the user to play. The unknown letters will
     * be substituted by underscore characters
     */
    protected TextView displayWord;

    /**
     * The feedback to the user based on the last results
     */
    protected TextView feedbackMessage;

    /**
     * The Figure that will be displayed during this game
     */
    protected Figure figure;

    /**
     * The gameplay instance
     */
    protected Gameplay gameplay;

    /**
     * Contains the preferences for this game
     */
    protected SharedPreferences preferences;

    /**
     * Contains the reader that the Dictionary can work with
     */
    protected DictionaryReader reader;

    /**
     * The filename of the dictionary file
     */
    protected String dictionaryFile;

    /**
     * This contains the SurfaceHolder instance where the
     * Figure can draw on
     */
    protected SurfaceHolder surfaceHolder;

    /**
     * Contains the History instance of this game
     */
    protected History history;

    /**
     * Flag set when the game is complete
     */
    protected boolean gameComplete = true;

    /**
     * Flag set when the desired word length should be reset
     */
    protected boolean resetWordLength = false;

    /**
     * Flag set when the number of stages of the figure should be altered
     */
    protected boolean resetFigureStages = false;

    /**
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view
        setContentView(R.layout.activity_new_game);

        // Initialize the activity parameters
        this.initParams();

        // Load the dictionary
        this.loadDictionaryReader();

        // Load the history instance
        this.loadHistory();

        // Load the gameplay instance
        this.loadGameplay();

        // Load the figure and it's view
        this.initFigure();

        // Start the game
        this.startGame();
    }

    /**
     * Start the menu
     *
     * @param menu
     * @return TRUE on success, FALSE on failure
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_game, menu);
        return true;
    }

    /**
     * Handle the action based on the selection option in the menu
     * by the player
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = this.getIntent();

        switch (id) {
            case R.id.action_settings :
                // Set a flag to reset the word length
                // The callback will fail because
                // this activity runs as single instance
                this.resetWordLength = true;

                intent.setClass(this, SettingsActivity.class);
                int longest_word_length;
                try {
                    String longest_word = this.gameplay.getDictionary().getLongestWord();
                    longest_word_length = longest_word.length();
                } catch (DictionaryReaderException ex) {
                    longest_word_length = HangMan.DEFAULT_WORD_LENGTH;
                }

                intent.putExtra("max_word_length", longest_word_length);
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

    /**
     * Resume the current game after interruption
     */
    @Override
    protected void onResume() {
        super.onResume();

        // If resumed from settings reset the values for the game
        if (this.resetWordLength) {
            Integer wordLength = this.preferences.getInt("wordLength", HangMan.DEFAULT_WORD_LENGTH);
            this.gameplay.setWordLength(1, wordLength);
            this.resetFigureStages = true;
            this.resetWordLength = false;
        }
    }

    /**
     * Store the SurfaceHolder when the SurfaceView is created
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        this.drawFigure();
    }

    /**
     * Execute actions when there was a change on the SurfaceView
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // No actions needed. The figure will be drawn with each guess
    }

    /**
     * Clean up after the SurfaceView is destroyed
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The holder is stored so that the figure can always draw to the canvast
        this.surfaceHolder = null;
    }

    /**
     * Start a new game in this activity
     * @param view
     */
    public void newGame(View view) {
        this.startGame();
    }

    /**
     * This method handles the guessed character by the player
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

    /**
     * Initialize the basic parameters for the application
     * to function properly
     */
    protected void initParams() {
        this.dictionaryFile = this.getString(R.string.dictionary_filename);
        this.preferences = this.getSharedPreferences(this.getString(R.string.preferences_name),
                                                        Context.MODE_PRIVATE);

        this.displayWord = (TextView) this.findViewById(R.id.displayWord);
        this.feedbackMessage = (TextView) this.findViewById(R.id.feedbackMessage);
    }

    /**
     * Load the gameplay instance
     */
    protected void loadGameplay() {
        if (this.gameplay != null) {
            return;
        }

        this.gameplay = new Gameplay();
        Integer wordLength = this.preferences.getInt("wordLength", HangMan.DEFAULT_WORD_LENGTH);
        this.gameplay.setWordLength(1, wordLength);
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

        // Set the figure for the current gameplay
        this.gameplay.setFigure(figure);

        // Load the figureview from the layout
        FigureView figureView = (FigureView)this.findViewById(R.id.figureView);
        figureView.setZOrderOnTop(true);    // necessary
        figureView.setFigure(this.figure);

        // Load the viewholder for this view
        SurfaceHolder figureViewHolder = figureView.getHolder();
        figureViewHolder.setFormat(PixelFormat.TRANSPARENT);
        figureViewHolder.addCallback(this);

        // Set the number of stages equal to the number of chances
        Integer chances = this.preferences.getInt("chances", HangMan.DEFAULT_CHANCES);
        this.figure.setNumberOfStages(chances);
    }

    /**
     * Load the dictionary reader instance
     */
    protected void loadDictionaryReader() {
        try {
            this.reader = new XMLReader(this.getApplicationContext().getAssets().open(this.dictionaryFile));
        } catch (IOException e) {
            this.feedbackMessage.setText(R.string.error_dictionary_io_exception);
        }
    }

    /**
     * Load the history instance
     */
    protected void loadHistory() {
        String filename = this.getFilesDir() + this.getString(R.string.history_filename);
        this.history = new History(filename);

        // Make the history file available throughout the application
        HangMan.setHistory(this.history);
    }

    /**
     * Start a new game
     */
    protected void startGame() {
        try {
            if (this.resetFigureStages) {
                Integer chances = this.preferences.getInt("chances", HangMan.DEFAULT_CHANCES);
                this.figure.setNumberOfStages(chances);
                this.resetFigureStages = false;
            }

            // Load the game
            gameplay.setReader(this.reader);
            gameplay.startGame();

            // Set the initial message for the feedback
            this.setFeedbackMessage(HangMan.NEW_GAME);
            this.feedbackMessage.setText(this.getResources().getString(R.string.gameplay_new_game));

            // Also reset the buttons to the original color
            this.resetKeyboardButtons();

            // Make sure the color of the text is black
            // this could be changed in a previous game
            this.displayWord.setTextColor(Color.BLACK);

            // Set the underscores for the word
            this.displayWord.setText(this.gameplay.getDisplayWord());

            // Enable the game
            this.gameComplete = false;
        } catch (DictionaryReaderException e) {
            this.feedbackMessage.setText(R.string.error_dictionary_read_exception);
        }
    }

    /**
     * Enter the guessed character and validate it's outcome
     * @param guess
     */
    protected void guessChar(String guess)  {
        int result = this.gameplay.enterChar(guess.charAt(0));

        this.setFeedbackMessage(result);

        if (result == HangMan.GAME_LOST) {
            this.displayWord.setTextColor(Color.RED);
            this.gameComplete = true;
        } else if (result == HangMan.GAME_WON) {
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
     * @param gamestate
     */
    protected void setFeedbackMessage(int gamestate) {
        String chances_remaining = String.format(this.getResources().getString(R.string.gameplay_chances_remaining),
                this.figure.getRemainingStages());;

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

    /**
     * Draw the figure on the canvas
     */
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
     * Transition to the highscores activity
     */
    protected void transitionToHighscores() {
        Intent intent = new Intent();
        intent.setClass(this, HighScoreActivity.class);
        this.startActivity(intent);
    }
}
