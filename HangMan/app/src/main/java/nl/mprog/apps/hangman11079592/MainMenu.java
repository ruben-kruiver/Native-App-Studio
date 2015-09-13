package nl.mprog.apps.hangman11079592;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hang_man, menu);
        return true;
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

    /**
     * Start a new single player game
     */
    public void startSinglePlayerGame(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        this.startActivity(intent);
    }

    /**
     * Start a new multiplayer game
     */
    public void startMultiPlayerGame(View view) {
        Intent intent = new Intent(this, AddPlayerActivity.class);
        this.startActivity(intent);
    }

    /**
     * Change the settings
     */
    public void ChangeSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }

    /**
     * Close the application
     */
    public void CloseApplication(View view) {
        finish();
        System.exit(0);
    }
}
