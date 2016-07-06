package tiimiss.globalgps.ba.tiimiss.Utillities;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by adismulabdic on 6/29/16.
 */
public class BaseUtillity {

    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
