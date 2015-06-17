package gaongil.safereturnhome.support;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PreferenceUtil {

    //TODO DELETE ONE, BETWEEN TWO METHOD
    // Stores the registration ID and Save Result that is sent to Server
    String registrationId();
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
