package gaongil.safereturnhome.network;

import retrofit.http.GET;
import retrofit.http.Path;

public interface WithNetwork {
    @GET("/test/{user}")
    boolean testRequest(@Path("user") String user);
}
