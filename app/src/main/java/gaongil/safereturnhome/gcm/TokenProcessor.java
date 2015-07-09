package gaongil.safereturnhome.gcm;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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
import gaongil.safereturnhome.exception.NetworkRequestFailureException;
import gaongil.safereturnhome.model.ResponseMessage;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.PreferenceUtil_;

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
    public void registerInBackground(String phoneNumber, String regId, String uuid) throws NetworkRequestFailureException {
        //네트워크 통신, 휴대전화번호에 해당하는 데이터가 존재할 경우, 새로 업로드하는 regId를 서버에 저장

        UserDTO dto = new UserDTO();
        dto.setPhoneNumber(phoneNumber);
        dto.setRegId(regId);
        dto.setUuid(uuid);

        sendRegIdAndPhoneNumberToServer(dto);
    }

    private void sendRegIdAndPhoneNumberToServer(UserDTO dto) throws NetworkRequestFailureException {
        ResponseMessage responseMessage = app.NETWORK.sendRegIdAndPhoneNumber(dto);

        Log.d(TAG, "responseMessage : "+responseMessage.toString());

        if (responseMessage.getCode() != Constant.NETWORK_RESPONSE_CODE_CREATION_NEW_DATA) {
            throw new NetworkRequestFailureException(responseMessage.getMessage());
        }

        //Save authToken
        preferenceUtil.sendTokenToServer().put(true);
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
