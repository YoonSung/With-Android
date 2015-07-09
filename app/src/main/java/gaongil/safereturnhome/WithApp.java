package gaongil.safereturnhome;

import android.app.Application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.androidannotations.annotations.EApplication;

import gaongil.safereturnhome.network.WithNetwork;
import gaongil.safereturnhome.support.Constant;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


@EApplication
public class WithApp extends Application {

    public static WithNetwork NETWORK;
    public static ObjectMapper JSONParser;

    @Override
    public void onCreate() {
        super.onCreate();

        // Network Set
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

        // Json Serializer
        JSONParser = new ObjectMapper();
        JSONParser.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        JSONParser.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JSONParser.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        JSONParser.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
    }

}
