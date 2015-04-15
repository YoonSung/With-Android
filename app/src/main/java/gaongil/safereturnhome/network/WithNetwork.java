package gaongil.safereturnhome.network;

import gaongil.safereturnhome.model.ResponseMessage;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface WithNetwork {
    @GET("/test/{user}")
    ResponseMessage testRequest(@Path("user") String user);

    @POST("/user")
    ResponseMessage sendRegId(@Body String regId);
}
