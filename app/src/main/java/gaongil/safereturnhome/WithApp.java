package gaongil.safereturnhome;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import gaongil.safereturnhome.network.WithNetwork;
import gaongil.safereturnhome.support.Constant;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


@EApplication
public class WithApp extends Application {

    public static WithNetwork NETWORK;


    @Override
    public void onCreate() {
        super.onCreate();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Cookies", "Retrofit-Sample-App");
            }
        };

        RestAdapter NETWORK_ADAPTER = new RestAdapter.Builder()
                .setEndpoint(Constant.NETWORK_ROOT_PATH)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        NETWORK = NETWORK_ADAPTER.create(WithNetwork.class);
    }

}
