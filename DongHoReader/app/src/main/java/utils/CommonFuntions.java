package utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import api.BarcodeReaderApiManager;
import model.BarcodeResponse;
import model.request.CustomerInfoChange;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonFuntions {

    public CommonFuntions() {
    }

    public void insertCustomerInfoChangeHistory(String tenDangNhap , Integer ms_kh, String so_dien_thoai ){

        CustomerInfoChange customerInfoChange = new CustomerInfoChange(tenDangNhap,ms_kh, so_dien_thoai);
        BarcodeReaderApiManager.getInstance().waterApi().insertCustomerInfoChangeHistory(customerInfoChange).enqueue(new Callback<BarcodeResponse>() {
            @Override
            public void onResponse(Call<BarcodeResponse> call, Response<BarcodeResponse> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(NhapSoBangTay.this, "Cập nhật lịch sử đồng hồ thành công!", Toast.LENGTH_LONG).show();
                    Log.i("CustomerInfoChange", "Cập nhật lS thay đổi sđt thành công" );

                } else {
                    Log.i("CustomerInfoChange", "Lỗi cập nhật ssđt" );

                }
            }

            @Override
            public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                Log.i("CustomerInfoChange", "update user pwd err : " + t.getMessage());
            }
        });
    }

    public byte[] ConverttoArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }




}
