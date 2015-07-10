package gaongil.safereturnhome.support;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.MediaStore.Images;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import gaongil.safereturnhome.scene.MainActivity_;

public class StaticUtils {

	private static String TAG = StaticUtils.class.getName();

	public static void centerToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
				0, 0);
		toast.show();
	}

	public static boolean checkNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();
		if (isWifiConn == true || isMobileConn == true)
			return true;
		centerToast(context, "네트워크 연결에 실패하였습니다.\n인터넷 연결상태를 확인해 주세요.");
		return false;
	}

	public static Bitmap scaleBitmap(Context context, Uri ImageUri,
			int newWidth, int newHeight) throws IOException {

		Bitmap bitmap = Images.Media.getBitmap(context.getContentResolver(),
				ImageUri);

		Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight,
				Config.ARGB_8888);

		float scaleX = newWidth / (float) bitmap.getWidth();
		float scaleY = newHeight / (float) bitmap.getHeight();
		float pivotX = 0;
		float pivotY = 0;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

		return scaledBitmap;
	}

	public static boolean isScreenOn(Context context) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
		//return pm.isInteractive();
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Create and show a simple notification containing the received GCM message.
	 *
	 * @param message GCM message received.
	 */
	public static void sendNotification(Context context, String message) {
		Intent intent = new Intent(context, context.getClass());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

		Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
				//.setSmallIcon(R.drawable.ic_stat_ic_notification)
				.setContentTitle("GCM Message")
				.setContentText(message)
				.setAutoCancel(true)
				.setSound(defaultSoundUri)
				.setContentIntent(pendingIntent);

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
	}
}
