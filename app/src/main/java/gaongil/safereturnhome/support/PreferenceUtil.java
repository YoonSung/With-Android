package gaongil.safereturnhome.support;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PreferenceUtil {

    //TODO DELETE ONE, BETWEEN TWO METHOD
    boolean sendTokenToServer();

    //TODO move to database
    String authToken();

    // Proper Profile Size to use custom image
    int profileSize();

    // Return Home Time
    int alarmHour();
    int alarmMinute();

    // User Last Status
    int statusEnumPosition();
}
