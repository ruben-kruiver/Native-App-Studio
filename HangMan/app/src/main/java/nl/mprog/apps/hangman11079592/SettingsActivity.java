package nl.mprog.apps.hangman11079592;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import nl.mprog.apps.hangman11079592.model.HangMan;

/**
 * This activity provides an interface for changing the
 * user preferences and game settings
 */
public class SettingsActivity extends Activity {

    /**
     * The chances seekbar
     */
    protected SeekBar seekbarChances;

    /**
     * The word length seekbar
     */
    protected SeekBar seekbarWordLength;

    /**
     * The shared preferences for this application
     */
    protected SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        this.saveSettings();

        switch (id) {

            case R.id.action_new_game:
                this.finish();
                break;

            case R.id.action_quit:
                this.finish();
                System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.saveSettings();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Load the seekbars from the view
        this.seekbarChances = (SeekBar) this.findViewById(R.id.seekbar_chances);
        this.seekbarWordLength = (SeekBar) this.findViewById(R.id.seekbar_word_length);

        Integer maxWordLength = this.getIntent().getIntExtra("maxWordLength", HangMan.DEFAULT_WORD_LENGTH);

        TextView sbWordLengthMaxValueText = (TextView) this.findViewById(R.id.max_word_length_text);

        // Set the maximum value on the progress bar equal to the longest word in the dictionary
        this.seekbarWordLength.setMax(maxWordLength);
        sbWordLengthMaxValueText.setText((maxWordLength.toString()));

        this.sharedPreferences = this.getSharedPreferences( this.getResources().getString(R.string.preferences_name),
                Context.MODE_PRIVATE);

        // Set the progress indicators on the progress bars to the current values
        this.setInitialProgressbarValues();
    }

    protected void setInitialProgressbarValues() {
        int chances = this.sharedPreferences.getInt("chances", HangMan.DEFAULT_CHANCES);
        int wordLength = this.sharedPreferences.getInt("wordLength", HangMan.DEFAULT_WORD_LENGTH);

        this.seekbarChances.setProgress(chances);
        this.seekbarWordLength.setProgress(wordLength);
    }

    protected void saveSettings() {
        int chances = this.seekbarChances.getProgress();
        int wordLength = this.seekbarWordLength.getProgress();

        // We store the settings in the shared preferences
        // so that these values can be restored when the
        // player comes back to this came some other time
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("chances", chances);
        editor.putInt("wordLength", wordLength);
        editor.commit();

        Intent data = this.getIntent();
        data.putExtra("wordLength", wordLength);
    }
}
