package gaongil.safereturnhome.support;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PreferenceUtil {

    @DefaultBoolean(false)
    boolean sendTokenToServer();

    String authToken();

    // Proper Profile Size to use custom image
    int profileSize();

    // Return Home Time
    int alarmHour();
    int alarmMinute();

    // User Last Status
    int statusEnumPosition();
}
