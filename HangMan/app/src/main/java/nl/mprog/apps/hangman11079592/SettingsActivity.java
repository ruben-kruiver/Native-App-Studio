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

import org.w3c.dom.Text;

import nl.mprog.apps.hangman11079592.model.Gameplay;
import nl.mprog.apps.hangman11079592.model.HangMan;

/**
 *
 */
public class SettingsActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    /**
     * The chances seekbar
     */
    protected SeekBar sbChances;

    /**
     * The word length seekbar
     */
    protected SeekBar sbWordLength;

    /**
     * The shared preferences for this application
     */
    protected SharedPreferences sharedPreferences;

    /**
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.sbChances = (SeekBar) this.findViewById(R.id.seekbar_chances);
        this.sbWordLength = (SeekBar) this.findViewById(R.id.seekbar_word_length);

        Integer max_word_length = this.getIntent().getIntExtra("max_word_length", HangMan.DEFAULT_WORD_LENGTH);

        TextView sbWordLengthMaxValueText = (TextView) this.findViewById(R.id.max_word_length_text);

        // Set the maximum value on the progress bar equal to the longest word in the dictionary
        this.sbWordLength.setMax(max_word_length);
        sbWordLengthMaxValueText.setText((max_word_length.toString()));

        this.sharedPreferences = this.getSharedPreferences( this.getResources().getString(R.string.preferences_name),
                                                            Context.MODE_PRIVATE);

        this.setInitialProgressValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.saveSettings();
        this.finish();
    }

    /**
     * Set the initial values of the seekbars
     */
    protected void setInitialProgressValues() {
        int chances = this.sharedPreferences.getInt("chances", HangMan.DEFAULT_CHANCES);
        int wordLength = this.sharedPreferences.getInt("wordLength", HangMan.DEFAULT_WORD_LENGTH);

        this.sbChances.setProgress(chances);
        this.sbWordLength.setProgress(wordLength);
    }

    /**
     * Save the current seekbar values
     */
    protected void saveSettings() {
        int chances = this.sbChances.getProgress();
        int wordLength = this.sbWordLength.getProgress();

        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt("chances", chances);
        editor.putInt("wordLength", wordLength);
        editor.commit();

        Intent data = this.getIntent();
        data.putExtra("wordLength", wordLength);

        this.setResult(HangMan.HISTORY_REQUEST, data);
    }
}
