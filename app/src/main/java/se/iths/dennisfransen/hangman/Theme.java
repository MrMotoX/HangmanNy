package se.iths.dennisfransen.hangman;

import android.app.Activity;
import android.content.Intent;

class Theme {
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_HALLOWEEN = 1;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_App);
                break;
            case THEME_HALLOWEEN:
                activity.setTheme(R.style.Theme_App_Halloween);
                break;
        }
    }
}