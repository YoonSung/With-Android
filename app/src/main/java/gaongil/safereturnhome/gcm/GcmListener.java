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

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EService;

import java.io.IOException;

import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.dto.cloud.ClientStrategy;
import gaongil.safereturnhome.dto.cloud.CloudMessage;
import gaongil.safereturnhome.dto.cloud.client.ClientDTO;
import gaongil.safereturnhome.exception.ProcessFailureException;

@EService
public class GcmListener extends GcmListenerService {

    private final String TAG = GcmListener.class.getName();

    @App
    WithApp app;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("msg");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        try {
            CloudMessage cloudMessage = app.ObjectMapper.readValue(message, CloudMessage.class);
            ClientStrategy clientStrategy = cloudMessage.getStrategy();
            ((ClientDTO) app.ObjectMapper.convertValue(cloudMessage.getData(), clientStrategy.getDTO())).process(clientStrategy, this.getApplicationContext());

        //TODO Add Queue
        } catch (IOException e) {
            e.printStackTrace();

        //TODO Add Queue
        } catch (ProcessFailureException e) {
            e.printStackTrace();
        }
        //sendNotification(message);
    }
    // [END receive_message]


}
