package nl.mprog.apps.hangman11079592;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nl.mprog.apps.hangman11079592.basemodel.HangManActivity;
import nl.mprog.apps.hangman11079592.model.HangMan;
import nl.mprog.apps.hangman11079592.model.history.Entry;

public class HighScoreActivity extends HangManActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        this.displayHighScores();
    }

    protected void displayHighScores() {
        ArrayList<Entry> highscores = HangMan.getHistoryInstance().getHistory();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Integer position = 1;
        for (Entry entry : highscores) {
            try {
                TextView valueScoreView = (TextView) this.findViewByDynamicId("value_score_", position);
                TextView valueTimeView = (TextView) this.findViewByDynamicId("value_time_", position);

                valueScoreView.setText(entry.getScore().toString());

                Date date = new Date(entry.getTime());
                valueTimeView.setText(formatter.format(date));

                // Stop at the top ten positions
                if (position == 10) {
                    break;
                }

                position++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
