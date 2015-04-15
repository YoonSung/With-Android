package gaongil.safereturnhome.model;

import gaongil.safereturnhome.R;

import java.util.ArrayList;

import android.content.Context;

public enum UserStatus {

    BAD(R.string.status_bad, R.drawable.ic_status_bad),
    FLAT(R.string.status_flat, R.drawable.ic_status_flat),
    HAPPY(R.string.status_happy, R.drawable.ic_status_happy),
    LOVELY(R.string.status_lovely, R.drawable.ic_status_lovely),
    NOTGOOD(R.string.status_notgood, R.drawable.ic_status_notgood),
    PUZZLED(R.string.status_puzzled, R.drawable.ic_status_puzzled),
    SAD(R.string.status_sad, R.drawable.ic_status_sad),
    SMILE(R.string.status_smile, R.drawable.ic_status_smile),
    SPEECHLESS(R.string.status_speechless, R.drawable.ic_status_speechless),
    SURPRISE(R.string.status_surprise, R.drawable.ic_status_surprise),
    ANGRY(R.string.status_angry, R.drawable.ic_status_angry);

    @SuppressWarnings("serial")
    private static ArrayList<UserStatus> userStatuses;


    public static ArrayList<UserStatus> getList() {

        if (userStatuses == null) {
            userStatuses = new ArrayList<UserStatus>() {
                {
                    add(BAD);
                    add(FLAT);
                    add(HAPPY);
                    add(LOVELY);
                    add(NOTGOOD);
                    add(PUZZLED);
                    add(SAD);
                    add(SMILE);
                    add(SPEECHLESS);
                    add(SURPRISE);
                    add(ANGRY);
                }
            };
        }

        return userStatuses;

    }

    private int stringId;
    private int imageResourceId;

    private UserStatus(int stringId, int resourceId) {
        this.stringId = stringId;
        this.imageResourceId = resourceId;
    }

    public String getStringValue(Context context) {
        return context.getResources().getString(stringId);
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
