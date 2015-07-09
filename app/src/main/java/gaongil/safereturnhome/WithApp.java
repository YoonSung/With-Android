package gaongil.safereturnhome;

import android.app.Application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.EApplication;
import org.json.JSONArray;

import gaongil.safereturnhome.network.WithNetwork;
import gaongil.safereturnhome.support.Constant;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


@EApplication
public class WithApp extends Application {

    public static WithNetwork NETWORK;
    public static ObjectMapper objectMapper;

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
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
    }

}
