package nl.mprog.apps.hangman11079592.basemodel;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import nl.mprog.apps.hangman11079592.R;

/**
 * This extension on the Activity class is made so that
 * various views in the activity layouts can be found
 * based on other values than the known identifier
 *
 * @author Ruben Kruiver
 * @since 2015
 * @version 0.1b
 */
public class HangManActivity extends Activity {
    /**
     * Get the view for a dynamic field
     */
    public View findViewByDynamicId(String prefix, Integer id) throws NoSuchFieldException, IllegalAccessException {
        return this.findViewByStringId(prefix + id.toString());
    }

    /**
     * Get the view for a dynamic field
     */
    public View findViewByStringId(String id) throws NoSuchFieldException, IllegalAccessException {
        Class resource = R.id.class;
        Field dynamicField = resource.getField(id);
        Integer fieldId = dynamicField.getInt(null);
        View view = this.findViewById(fieldId);

        return view;
    }
}
