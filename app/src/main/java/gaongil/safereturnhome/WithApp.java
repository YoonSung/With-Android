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
import retrofit.converter.JacksonConverter;


@EApplication
public class WithApp extends Application {

    public static WithNetwork NETWORK;
    public static ObjectMapper ObjectMapper;

    @Override
    public void onCreate() {
        super.onCreate();

        // --- Jackson ObjectMapper
        ObjectMapper = new ObjectMapper();
        ObjectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        ObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        ObjectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE);


        // --- Retrofit Network Set
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
                .setConverter(new JacksonConverter(ObjectMapper))
                .build();

        NETWORK = NETWORK_ADAPTER.create(WithNetwork.class);
    }

}
