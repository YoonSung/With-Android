package gaongil.safereturnhome.scene;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.model.ResponseMessage;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.PreferenceUtil_;

@EActivity(R.layout.activity_splash)
public class SplashScreen extends Activity {

    private static final String TAG = SplashScreen.class.getName();

    @App
    WithApp app;

    @Pref
    PreferenceUtil_ preferenceUtil;
	private GoogleCloudMessaging gcm;

	/** Check if the app is running. */
	private boolean isRunning;

	// You need to do the Play Services APK check here too.
	@Override
	protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        checkPlayServices();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Smooth transition, full activity to non full activity
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
	void init() {
		checkEssentialInfomation();
	}

	private void checkEssentialInfomation() {
		// Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        // Otherwise, prompt user to get valid Play Services APK.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            String regId = preferenceUtil.registrationId().get();

            Log.d(TAG, "regId : "+regId);

            if (regId == null || regId.isEmpty()) {
                registerInBackground();
            }

            int profileImageSize = preferenceUtil.profileSize().get();

            if (profileImageSize == 0) {
            	saveProfileImageWidth();
            }

            isRunning = true;
            startSplash();

        } else {
            //TODO Alert to user
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
	}

	@SuppressLint("NewApi")
    private void saveProfileImageWidth() {
		Display display = getWindowManager().getDefaultDisplay();

        int deviceWidth;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        }
        else{
            deviceWidth = display.getWidth();
        }

        preferenceUtil.profileSize().put(deviceWidth / Constant.PROFILE_IMAGE_RATE_BY_DEVICE_WIDTH);
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, Constant.MESSAGE_PLAY_SERVICES_ERROR);
	            finish();
	        }
	        return false;
	    }
	    return true;
	}

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(final String regId) {

        ResponseMessage response = app.NETWORK.sendRegId(regId);
        boolean result = (boolean) response.getData();
        Log.d(TAG, "response result : "+response.toString());
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    @Background
    public void registerInBackground() {
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(SplashScreen.this);
            }
            String regId = gcm.register(Constant.PROJECT_ID);
            Log.d(TAG, "Device registered, registration ID=" + regId);

            //TODO 네트워크 통신, 휴대전화번호에 해당하는 데이터가 존재할 경우, 해당 regId를 반환

            // You should send the registration ID to your server over HTTP, so it
            // can use GCM/HTTP or CCS to send messages to your app.

            //TODO 서버에 등록이 성공하면, local 데이터로 regId를 저장
            sendRegistrationIdToBackend(regId);

            // Persist the regID - no need to register again.

            //TODO DELETE
            //common.storeRegistrationId(regId);
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.d(TAG, "Error :" + ex.getMessage());
            // If there is an error, don't just keep trying to register.
            // Require the user to click a button again, or perform
            // exponential back-off.
        }
    }

	private void startSplash() {

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					Thread.sleep(Constant.SPLASH_WAIT_TIME);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					runOnUiThread(new Runnable() {
						@Override
						public void run()
						{
							doFinish();
						}
					});
				}
			}
		}).start();
	}

	/**
	 * If the app is still running than this method will start the Login activity
	 * and finish the Splash.
	 */
	private synchronized void doFinish() {

		if (isRunning) {
			isRunning = false;
			Intent i = new Intent(SplashScreen.this, MainActivity_.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isRunning = false;
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
