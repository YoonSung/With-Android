package gaongil.safereturnhome.network;

import gaongil.safereturnhome.model.ResponseMessage;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface WithNetwork {

    @POST("/user")
    ResponseMessage sendRegIdAndPhoneNumber(@Body String regId, @Body String phoneNumber);
}
