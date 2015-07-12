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
    /**
     * send the registration ID to server over HTTP, so it
     * can use GCM/HTTP or CCS to send messages to app.
     */
    public void registerInBackground(String phoneNumber, String regId, String uuid) {
        //네트워크 통신, 휴대전화번호에 해당하는 데이터가 존재할 경우, 새로 업로드하는 regId를 서버에 저장

        UserDTO dto = new UserDTO();
        dto.setPhoneNumber(phoneNumber);
        dto.setRegId(regId);
        dto.setUuid(uuid);

        sendRegIdAndPhoneNumberToServer(dto);
    }

    private void sendRegIdAndPhoneNumberToServer(UserDTO dto) {

        Callback<Response> tokenCallback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

            }

            @Override
            public void failure(RetrofitError error) {
                preferenceUtil.sendTokenToServer().put(true);
            }
        };

        app.NETWORK.sendRegIdAndPhoneNumber(dto, new Callback<ResponseMessage>() {
            @Override
            public void success(ResponseMessage responseMessage, Response response) {
                Log.d(TAG, "responseMessage : " + responseMessage.toString());

                if (responseMessage.getCode() == Constant.NETWORK_RESPONSE_CODE_CREATION_NEW_DATA) {
                    try {
                        // Save authToken Received from server
                        String tokenValue = getToken(response);
                        preferenceUtil.authToken().put(tokenValue);

                        // Save sendTokenFlag true
                        preferenceUtil.sendTokenToServer().put(true);
                    } catch (Exception e) {
                        preferenceUtil.sendTokenToServer().put(false);
                    }

                } else if (responseMessage.getCode() == Constant.NETWORK_RESPONSE_CODE_OK){

                    // TODO Refactoring
                    StaticUtils.centerToast(getApplicationContext(), "Welcome Back!");
                    preferenceUtil.sendTokenToServer().put(true);
                }
            }

            private String getToken(Response response) throws Exception {
                String cookieValue = null;

                for(Header header : response.getHeaders()) {
                    if (header.getValue().equalsIgnoreCase("Set-Cookie")) {
                        cookieValue = header.getValue();
                        break;
                    } else {
                        continue;
                    }
                }

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

            registerInBackground(phoneNumber, token, uuid);

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
