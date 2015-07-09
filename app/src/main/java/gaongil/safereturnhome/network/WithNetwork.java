package gaongil.safereturnhome.network;

import gaongil.safereturnhome.dto.UserDTO;
import gaongil.safereturnhome.model.ResponseMessage;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface WithNetwork {

    @POST("/users")
    ResponseMessage sendRegIdAndPhoneNumber(@Body UserDTO user);

    @PUT("/users/token")
    ResponseMessage updateRegId(String updatedToken);

    //TODOjm
    ResponseMessage createGroup();
}
