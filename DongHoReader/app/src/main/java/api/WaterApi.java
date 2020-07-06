package api;


import model.BarcodeResponse;
import model.request.CustomerInfoChange;
import model.request.MeterResetHis;
import model.request.MeterUpdateKhongDocDuocRequest;
import model.request.MeterUpdateRequest;
import model.request.UpdatePhoneNumber;
import model.response.ThongTinDaoSo;
import model.response.ThongTinDongHo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WaterApi {

    @PUT("updatecsm/{id}")
    Call<BarcodeResponse> updateMeter(@Path("id") int id, @Body MeterUpdateRequest meterRequest);

    @PUT("updatekodocduoc/{id}")
    Call<BarcodeResponse> updateMeterKhongDocDuoc(@Path("id") int id, @Body MeterUpdateKhongDocDuocRequest request);

    @GET("GetWaterMeterInfo")
    Call<ThongTinDongHo> getWaterMeterInfo(@Query("ms_dh") Integer ms_dh);

    @GET("getthongtindaoso")
    Call<ThongTinDaoSo> GetWaterMeterResetNumber(@Query("ms_dh") Integer ms_dh);

    @POST("insertlsds")
    Call<BarcodeResponse> insertMeterResetHistory(@Body MeterResetHis meterResetHis);

    @PUT("UserUpdatePhoneNumber/{id}")
    Call<BarcodeResponse> updatePhoneNumber(@Path("id") String id, @Body UpdatePhoneNumber updatePhoneNumber);

    @POST("InsertCustomerInfoChangeHistory")
    Call<BarcodeResponse> insertCustomerInfoChangeHistory(@Body CustomerInfoChange customerInfoChange);

}
