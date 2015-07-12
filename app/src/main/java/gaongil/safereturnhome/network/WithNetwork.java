package gaongil.safereturnhome.network;

import gaongil.safereturnhome.dto.UserDTO;
import gaongil.safereturnhome.model.ResponseMessage;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface WithNetwork {

    @FormUrlEncoded
    @POST("/users")
    void sendRegIdAndPhoneNumber(@Field("phoneNumber") String phoneNumber, @Field("regId") String regId, @Field("uuid") String uuid, Callback<ResponseMessage> callback);

    @PUT("/users/{userId}/token")
    ResponseMessage updateRegId(@Path("userId") String userId,String updatedToken);

    //@POST("/groups")
    //ResponseMessage createGroup(@Body GroupDTO groupDTO);
}
