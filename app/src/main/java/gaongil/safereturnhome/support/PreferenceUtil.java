package gaongil.safereturnhome.support;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface PreferenceUtil {
	
    // Stores the registration ID and the app versionCode in the application's
    String registrationId();
    int appVersion();

    // Proper Profile Size to use custom image
    int profileSize();

    // Return Home Time
    int alarmHour();
    int alarmMinute();

    // User Last Status
    int statusEnumPosition();
}
