/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gaongil.safereturnhome.gcm;

import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;

import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.exception.NetworkRequestFailureException;
import gaongil.safereturnhome.model.ResponseMessage;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.PreferenceUtil_;

@EService
public class TokenChangeListener extends InstanceIDListenerService {

    private final String TAG = TokenChangeListener.class.getName();

    @App
    WithApp app;

    @Pref
    PreferenceUtil_ preferenceUtil;

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        try {
            sendTokenToUpdate();
        } catch (IOException e) {
            //TODO FAILOVER
            e.printStackTrace();
        } catch (NetworkRequestFailureException e) {
            //TODO FAILOVER
            e.printStackTrace();
        }
    }

    // TODO
    private void sendTokenToUpdate() throws NetworkRequestFailureException, IOException {
        // [START register_for_gcm]
        // Initially this call goes out to the network to retrieve the token, subsequent calls
        // are local.
        // [START get_token]
        InstanceID instanceID = InstanceID.getInstance(getApplication());
        String token = instanceID.getToken(Constant.PROJECT_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        // [END get_token]
        Log.d(TAG, "GCM Registration Token: " + token);

        //ResponseMessage responseMessage = app.NETWORK.updateRegId(token);
        /*
        if (responseMessage.getCode() != Constant.NETWORK_RESPONSE_CODE_OK) {
            throw new NetworkRequestFailureException(responseMessage.getMessage());
        }
        */
    }

    // [END refresh_token]
}
