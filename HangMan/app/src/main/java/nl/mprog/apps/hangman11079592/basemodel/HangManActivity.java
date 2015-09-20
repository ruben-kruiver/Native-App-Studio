package nl.mprog.apps.hangman11079592.basemodel;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import nl.mprog.apps.hangman11079592.R;

/**
 * Created by Ruben on 19-9-2015.
 */
public class HangManActivity extends Activity {
    /**
     * Get the view for a dynamic field
     */
    protected View findViewByDynamicId(String prefix, Integer id) throws NoSuchFieldException, IllegalAccessException {
        return this.findViewByStringId(prefix + id.toString());
    }

    /**
     * Get the view for a dynamic field
     */
    protected View findViewByStringId(String id) throws NoSuchFieldException, IllegalAccessException {
        Class resource = R.id.class;
        Field dynamicField = resource.getField(id);
        Integer fieldId = dynamicField.getInt(null);
        View view = this.findViewById(fieldId);

        return view;
    }
}
