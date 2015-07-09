package gaongil.safereturnhome.scene;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.UUID;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.exception.WithNotFoundException;
import gaongil.safereturnhome.gcm.TokenProcessor_;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.PreferenceUtil_;
import gaongil.safereturnhome.support.StaticUtils;

@EActivity(R.layout.activity_splash)
//TODO 네트워크 연결여부 확인
public class SplashScreen extends Activity {

    private static String TAG = SplashScreen.class.getName();

    @Pref
    PreferenceUtil_ preferenceUtil;

	/** Check if the app is running. */
	private boolean isRunning;

    private BroadcastReceiver completeTokenGenerationReceiver;

	@Override
	protected void onResume() {
        super.onResume();
	}

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiverIfExist();
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
        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        // Otherwise, prompt user to get valid Play Services APK.
        checkPlayServices();

        String authToken = getAuthToken();
        Log.d(TAG, "authToken -> " + authToken);

        if (authToken == null)
            register();
        else
            startSplash();
    }

    private void registerReceiver() {

        if(completeTokenGenerationReceiver != null)
            return;

        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(Constant.BROADCAST_ACTION_TOKEN_GENERATE);

        this.completeTokenGenerationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive Entrance");
                if (!intent.getAction().equals(Constant.BROADCAST_ACTION_TOKEN_GENERATE))
                    return;

                if (isSendTokenToServer() == false) {
                    centerToastAndFinish(getResources().getString(R.string.alert_gcm_register_fail));

                } else {

                    if (isProfileImageSizeExist() == false)
                        saveProfileImageWidth();

                    startSplash();
                }
            }
        };

        this.registerReceiver(this.completeTokenGenerationReceiver, theFilter);
    }

    private void unregisterReceiverIfExist() {
        if(completeTokenGenerationReceiver != null)
            this.unregisterReceiver(completeTokenGenerationReceiver);
    }

	private void register() {

        try {

            String phoneNumber = getPhoneNumber();
            String uuid = UUID.randomUUID().toString();

            Log.d(TAG, "phoneNumber -> "+phoneNumber);
            Log.d(TAG, "uuid -> "+uuid);

            //1. Phone Number Check
            if (!isValidPhoneNumber(phoneNumber))
                centerToastAndFinish(getString(R.string.alert_absent_phone_number));

            if (isSendTokenToServer() == false) {
                registerReceiver();
                TokenProcessor_.intent(getApplication())
                        .registerUserToServer(phoneNumber, uuid)
                        .start();
            }

        } catch (WithNotFoundException e) {
            e.printStackTrace();
            centerToastAndFinish(e.getMessage());
        }
	}

    private String getAuthToken() {
        return preferenceUtil.authToken().getOr(null);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("010"))
            return false;

        if (phoneNumber.length() != 11)
            return false;

        return true;
    }

    private boolean isSendTokenToServer() {
        return preferenceUtil.sendTokenToServer().get();
    }

    @SuppressLint("NewApi")
    private void saveProfileImageWidth() {
		Display display = getWindowManager().getDefaultDisplay();

        int deviceWidth;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        } else {
            deviceWidth = display.getWidth();
        }

        //Save Profile Size With device's 1/4 rate
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


    @UiThread
    public void centerToast(String message) {
        StaticUtils.centerToast(this, message);
    }


    @UiThread
    public void centerToastAndFinish(String message) {
        centerToast(message);
        this.finish();
    }

    private String getPhoneNumber() throws WithNotFoundException {
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String number = telephonyManager.getLine1Number();

        if (number.trim().replaceAll(" ", "").length() != 11)
            throw new WithNotFoundException();

        return number;
    }

    private boolean isProfileImageSizeExist() {
        return preferenceUtil.profileSize().get() == 0 ? false : true;
    }

    private void startSplash() {
        isRunning = true;
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
