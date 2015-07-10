package gaongil.safereturnhome.dto.cloud.client;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.dto.cloud.ClientStrategy;
import gaongil.safereturnhome.dto.cloud.Strategy1;
import gaongil.safereturnhome.exception.ProcessFailureException;
import gaongil.safereturnhome.support.StaticUtils;

/**
 * Created by yoon on 15. 7. 10..
 */
public class PlainText implements ClientDTO {

    private final String TAG = PlainText.class.getName();

    private String text;

    private PlainText(){}

    public PlainText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "PlainText{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public void process(ClientStrategy strategy, Context context) throws ProcessFailureException {
        if (strategy == Strategy1.CHAT_MESSAGE) {
            processChatMessage(context);
        } else {
            throw new ProcessFailureException();
        }
    }

    private void processChatMessage(Context context) {
        Log.d(TAG, this.text);
        //TODO add break window and notify to user
        StaticUtils.sendNotification(context, this.text);
    }
}
