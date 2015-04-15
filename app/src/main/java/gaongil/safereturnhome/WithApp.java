package gaongil.safereturnhome;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.EApplication;

import gaongil.safereturnhome.network.WithNetwork;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.StaticUtils;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


@EApplication
public class WithApp extends Application {

    public static WithNetwork NETWORK;


    @Override
    public void onCreate() {
        super.onCreate();

        RestAdapter NETWORK_ADAPTER = new RestAdapter.Builder()
                .setEndpoint(Constant.NETWORK_ROOT_PATH)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        NETWORK = NETWORK_ADAPTER.create(WithNetwork.class);
    }

}
