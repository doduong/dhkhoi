package api;

import model.BarcodeResponse;
import model.request.UpdateUserPwd;
import model.response.UserLoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountApi {

    @PUT("changepwd/{id}")
    Call<ResponseBody> updateUserPassword(@Path("id") String userId, @Body UpdateUserPwd pwdRequest);

    @GET("checklogin")
    Call<UserLoginResponse> checkLogin(@Query("ms_bd") String msdb, @Query("mat_khau") String pwd);

    @GET("GetCurrentAppVersion")
    Call<UserLoginResponse> getVersion();

}
