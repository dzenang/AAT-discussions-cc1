package granulo.dzenan.android.aatdiscussionscc1.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dzenang on 30.10.2017.
 */

public interface APIService {

        @POST("/sest")
        Call<Void> postExample(@Body Example e);

}
