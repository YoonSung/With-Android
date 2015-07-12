package gaongil.safereturnhome.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.support.app.AbstractIntentService;

import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.dto.UserDTO;
import gaongil.safereturnhome.exception.WithNotFoundException;
import gaongil.safereturnhome.model.ResponseMessage;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.PreferenceUtil_;
import gaongil.safereturnhome.support.StaticUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by yoon on 15. 7. 9..
 */
@EIntentService
public class TokenProcessor extends AbstractIntentService {

    private static String TAG = TokenProcessor.class.getName();

    @Pref
    PreferenceUtil_ preferenceUtil;

    @App
    WithApp app;

    public TokenProcessor() {
        super("EMPTY");
    }

    private void sendRegIdAndPhoneNumberToServer(String phoneNumber, String regId, String uuid) {

        app.NETWORK.sendRegIdAndPhoneNumber(phoneNumber, regId, uuid, new Callback<ResponseMessage>() {
            @Override
            public void success(ResponseMessage responseMessage, Response response) {
                Log.d(TAG, "responseMessage : " + responseMessage.toString());

                if (responseMessage.getCode() == Constant.NETWORK_RESPONSE_CODE_CREATION_NEW_DATA) {
                    saveToken(response);

                } else if (responseMessage.getCode() == Constant.NETWORK_RESPONSE_CODE_OK){
                    saveToken(response);
                    // TODO Refactoring
                    StaticUtils.centerToast(getApplicationContext(), "Welcome Back!");
                }
                // Receive TODO Update Code
                //} else if (responseMessage.getCode() == Constant.NETWORK)
            }

            private void saveToken(Response response) {
                try {
                    // Save authToken Received from server
                    String tokenValue = getToken(response);
                    preferenceUtil.authToken().put(tokenValue);

                    // Save sendTokenFlag true
                    preferenceUtil.sendTokenToServer().put(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    preferenceUtil.sendTokenToServer().put(false);
                }
            }

            private String getToken(Response response) throws Exception {
                String cookieValue = null;

                String key;
                String value;
                for(Header header : response.getHeaders()) {
                    key = header.getName();
                    value = header.getValue();

                    if (key == null)
                        continue;

                    if (key.equalsIgnoreCase("Set-Cookie")) {
                        cookieValue = header.getValue();
                        break;
                    } else {
                        continue;
                    }
                }

                Log.i(TAG, "cookieValue : "+cookieValue);
                String[] cookieEntries = cookieValue.split(";");

                for (String cookieEntry : cookieEntries) {
                    cookieEntry = cookieEntry.trim();
                    if (cookieEntry.startsWith(Constant.NETWORK_AUTH_COOKIE_NAME)) {
                        return cookieEntry.replace(Constant.NETWORK_AUTH_COOKIE_NAME +"=","");
                    }
                }

                throw new WithNotFoundException();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @ServiceAction
    void registerUserToServer(String phoneNumber, String uuid) {
        Log.d(TAG, "pheonNumber : "+ phoneNumber + ", uuid : "+uuid);
        // [START register_for_gcm]
        // Initially this call goes out to the network to retrieve the token, subsequent calls
        // are local.
        // [START get_token]
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(Constant.PROJECT_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, "GCM Registration Token: " + token);

            sendRegIdAndPhoneNumberToServer(phoneNumber, token, uuid);

            preferenceUtil.sendTokenToServer().put(true);

        } catch (Exception e) {
            e.printStackTrace();
            preferenceUtil.sendTokenToServer().put(false);

        }
        // [END get_token]

        //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_TOKEN_GENERATE));
        sendBroadcast(new Intent(Constant.BROADCAST_ACTION_TOKEN_GENERATE));
    }
}
