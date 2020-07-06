package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import api.BarcodeReaderApiManager;
import model.BarcodeResponse;
import model.DiemDungMatMang;
import model.DongHoNoi;
import model.SoTieuThu;
import model.TinhtrangDocDongHo;
import model.request.MeterResetHis;
import model.request.MeterUpdateRequest;
import model.request.UpdatePhoneNumber;
import model.response.ThongTinDaoSo;
import model.response.ThongTinDongHo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CommonFuntions;
import utils.CommonText;
import utils.DBManager;
import utils.ReadJson;
import utils.Server;
import utils.SharedPref;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

public class NhapSoActivity extends Activity {

    TextView tvTenTuyen;
    EditText edtdanhba;
    EditText edttenkh;
    EditText edtdiachi;
    EditText edtSocu;
    EditText edtSomoi;
    EditText edttieuthu;
    EditText edtngaydoccu;
    EditText edtngaydocmoi;
    EditText edtmdsd;
    EditText edtsodongho1;
    EditText txtTT3T;
    EditText txtTTTB;
    EditText edtSDT;
    EditText edtghichu;

    Button btnCall;
    Button btnLuuSDT;
    Button btnLuu;
    Button btntruoc;
    Button btnsau;

    String ms_tk = "";
    String ms_tuyendoc = "";
    String ms_bd;
    Integer ms_db;

    CommonText common = new CommonText();
    CommonFuntions commonFuntions ;
    int makhongdocduoc = 0;
    Integer chisomoi = null;
    Integer chisocu = null;
    Integer luongtieuthu = null;
    int temp = 1;
    Integer ms_dh = null;
    public static Connection conn;
    ArrayList<SoTieuThu> arrstt = new ArrayList<SoTieuThu>();
    final DBManager dbManager = new DBManager(this);
    SharedPref config;
    Integer so_tthu_cu = 0;
    CommonFuntions cmfuntion;

    // khái báo biến image upload
    private ImageButton btnPhoto;
    ImageView imgPhoto;
    Bitmap selectedBitmap;
    String ba1;
    private final static int TAKE_PHOTO = 1;

    //end
    String savesdttemp= "";
    private Uri photoUri;
    private final static String PHOTO_URI = "photoUri";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_so);
        config = new SharedPref(this);
        commonFuntions = new CommonFuntions();
        Intent intent = getIntent();
        ms_bd = config.getString("ms_bd", "");
        ms_tk = config.getString("ms_tk","");
        ms_tuyendoc = config.getString("ms_tuyen","");
        ms_db = Integer.parseInt(intent.getStringExtra("ms_db"));

        ms_dh = Integer.parseInt(intent.getStringExtra("ms_dh"));
        cmfuntion = new CommonFuntions();
        //Log.d("ms_db", ms_db);

        khaibaobien();
        getThongTinKhachHang(ms_db);
        GetTopNInvoice(ms_db);
        GetPurposeByMoinoi(ms_db);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnPhoto = (ImageButton) findViewById(R.id.btncamera);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra Camera trong thiết bị
                if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    // Mở camera mặc định
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Tiến hành gọi Capture Image intent
                    //startActivityForResult(intent, 100);

                    //phien ban vsmart
                   photoUri = getContentResolver().insert(
                            EXTERNAL_CONTENT_URI, new ContentValues());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);

                    //End Vsmart

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, TAKE_PHOTO);
                    }

                } else {
                    Toast.makeText(getApplication(), "Camera không được hỗ trợ", Toast.LENGTH_LONG).show();
                }


            }
        });
        //phien ban vsmart
        if (savedInstanceState != null){
            photoUri = (Uri) savedInstanceState.get(PHOTO_URI);
        }
        //End Vsmart

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NhapSoActivity.this, TabActivity.class);
        intent.putExtra("ms_tuyen", ms_tuyendoc);
        intent.putExtra("ms_tk", ms_tk);
        intent.putExtra("ms_bd", ms_bd);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == TAKE_PHOTO || requestCode == 200) && resultCode == RESULT_OK) {
            //if (null != data) {
            //  if (null != data.getExtras()) {
            //      {
            //Log.d("image",data.getExtras().get("data").toString() );
            //selectedBitmap = getResizedBitmap((Bitmap) data.getExtras().get("data"), 400);

                        //Phiên bản vsmart
                        try {
                            InputStream stream = getContentResolver().openInputStream(photoUri);
                            selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 400);
                            int i = getContentResolver().delete(photoUri, null, null);
                            Log.e("getContentResolver:", ""+i);


                        } catch (FileNotFoundException e) {
                            Log.e("Error", "FileNotFound",e);
                        }
                        //End phien ban Vsmart

            imgPhoto.setImageBitmap(selectedBitmap);
            //   }
            // }
            //}
        }

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }




    public void khaibaobien() {

        tvTenTuyen = (TextView) findViewById(R.id.tentuyen);
        edtdanhba = (EditText) findViewById(R.id.edtdanhba);
        edttenkh = (EditText) findViewById(R.id.edttennguoithue);
        edtdiachi = (EditText) findViewById(R.id.edtDiaChi);
        edtSocu = (EditText) findViewById(R.id.edtSocu);
        edtSomoi = (EditText) findViewById(R.id.edtSomoi);
        edttieuthu = (EditText) findViewById(R.id.edttieuthu);
        edtngaydoccu = (EditText) findViewById(R.id.edtngaydoccu);
        edtngaydocmoi = (EditText) findViewById(R.id.edtngaydocmoi);
        edtsodongho1 = (EditText) findViewById(R.id.edtsodongho);
        txtTT3T = (EditText) findViewById(R.id.txtTT3T);
        txtTTTB = (EditText) findViewById(R.id.txtTBTT);
        edtSDT = (EditText) findViewById(R.id.edtSDT);
        btnLuuSDT = (Button) findViewById(R.id.btnLuuSDT);
        edtghichu = (EditText) findViewById(R.id.edtghichu);
        edtmdsd = (EditText) findViewById(R.id.edtmdsdkh);
        btnLuu = (Button) findViewById(R.id.btnLuuChiSoMoi);
        edtSomoi.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        edttieuthu.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        edtSDT.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

        btnLuuSDT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (isConnected()) {

                    if (!"".equals(edtSDT.getText().toString())) {
                        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
                        b.setTitle("Thông báo");
                        b.setMessage("Bạn muốn cập nhật số điện thoại mới: " + edtSDT.getText().toString());
                        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                updatePhoneNumber(ms_db, edtSDT.getText().toString());

                                //Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();


                            }
                        });
                        b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        b.create().show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Số điện thoại không được để trống", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Không có kết nối internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                if (coNguyenNhanVaAnh()) {

                    if (!"".equals(edtSomoi.getText().toString())) {
                        chisomoi = Integer.parseInt(edtSomoi.getText().toString());
                        chisocu = Integer.parseInt(edtSocu.getText().toString());
                        luongtieuthu = tinhLuongTieuThu(chisomoi, chisocu);
                        if (null != luongtieuthu && !"".equals(edttieuthu.getText().toString())) {
                            if (chisomoi > chisocu) {

                                if (!canhbaochenhlech(luongtieuthu)) {
                                    cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi();
                                } else {
                                    cap_nhat_canh_bao_chi_so_moi_vuot_nguong();
                                }
                            } else if (luongtieuthu == 0) {
                                canh_bao_cap_nhat_luong_tieu_thu_bang_khong();

                            } else {
                                cap_nhat_luong_tieu_thu_nho_hon_khong_dh_quay_vong(luongtieuthu, ms_dh, ms_db, chisocu, chisomoi);
                            }
                        } else {
                            edttieuthu.setEnabled(false);
                            Toast.makeText(NhapSoActivity.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        canh_bao_chi_so_moi_de_trong();

                    }

                } else {
                    canh_bao_khong_co_nguyen_nhan_va_anh();
                }
            }
        });

       /* btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + edtSDT.getText().toString()));
                startActivity(callIntent);
            }
        });*/


        edtSomoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!"".equals(edtSomoi.getText().toString())&&!"".equals(edtSocu.getText().toString())) {
                    luongtieuthu = tinhLuongTieuThu(Integer.parseInt(edtSomoi.getText().toString()), Integer.parseInt(edtSocu.getText().toString()));
                }else{
                    edttieuthu.setText("");
                }
            }
        });
    }

    private void updatePhoneNumber(final Integer ms_mnoi, String sdt) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Đang cập nhật...");
        progressDialog.show();

        UpdatePhoneNumber request = new UpdatePhoneNumber(ms_mnoi,sdt);
        BarcodeReaderApiManager.getInstance().waterApi().updatePhoneNumber( ""+ms_mnoi,request).enqueue(new Callback<BarcodeResponse>() {
            @Override
            public void onResponse(Call<BarcodeResponse> call, Response<BarcodeResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    commonFuntions.insertCustomerInfoChangeHistory(ms_bd,ms_mnoi, savesdttemp);
                    if(null!= edtSDT.getText()) {
                        savesdttemp = edtSDT.getText().toString();
                    }
                    Toast.makeText(NhapSoActivity.this, "Cập nhật số điện thoại thành công!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(NhapSoActivity.this, "Lỗi cập nhật số điện thoại", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.i("LOGIN", "update user pwd err : " + t.getMessage());

            }
        });


    }

    public boolean coNguyenNhanVaAnh() {

        if ( imgPhoto.getDrawable() == null) {
            return false;
        }
        return true;

    }
    public void canh_bao_khong_co_nguyen_nhan_va_anh() {
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Bạn phải chụp ảnh trước!");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();

    }

    private Integer tinhLuongTieuThu(final Integer chisomoi, final Integer chisocu ){


        BarcodeReaderApiManager.getInstance().waterApi().getWaterMeterInfo(ms_dh).enqueue(new Callback<ThongTinDongHo>() {
            @Override
            public void onResponse(Call<ThongTinDongHo> call, retrofit2.Response<ThongTinDongHo> response) {
                Integer he_so = response.body().getHe_so();
                Integer kha_nang_dh = response.body().getKha_nang_dh();
                if(chisomoi<chisocu) {
                    luongtieuthu = (chisomoi + kha_nang_dh - chisocu + 1) * he_so + so_tthu_cu;
                }else {
                    luongtieuthu = (chisomoi - chisocu) * he_so + so_tthu_cu;
                }
                edttieuthu.setText(String.valueOf(luongtieuthu));

            }

            @Override
            public void onFailure(Call<ThongTinDongHo> call, Throwable t) {

            }
        });

        return luongtieuthu;


    }

    public void canh_bao_chi_so_moi_de_trong() {
       // Log.d("btnLuu:", "không checkbok- khong nhap chi so moi");
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Bạn phải nhập chỉ số mới!");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                edtSomoi.requestFocus();

            }
        });
        b.create().show();
    }

    public void cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi() {
        if (isConnected()) {

            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
            b.setTitle("Thông báo");
            b.setMessage("Bạn muốn cập nhật chỉ số mới: " + String.valueOf(chisomoi) + ", Lượng tiêu thụ: " + luongtieuthu);
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    capnhatsomoiDBTam(luongtieuthu);
                    updateChiSoMoi( luongtieuthu, false);
                    Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                    intent.putExtra("ms_tuyen", ms_tuyendoc);
                    intent.putExtra("ms_tk", ms_tk);
                    intent.putExtra("ms_bd", ms_bd);
                    startActivity(intent);


                }
            });
            b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();


        } else {
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
            b.setTitle("Cảnh báo");
            b.setMessage("Mất kết nối internet! Cập nhật điểm dùng vào bảng tạm");
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    capnhatDiemDungMatMang(luongtieuthu);


                }
            });
            b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();


        }
    }

    public void canh_bao_cap_nhat_luong_tieu_thu_bang_khong() {
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Lượng tiêu thụ bằng 0. Bạn chắc chắn muốn nhập?");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!canhbaochenhlech(luongtieuthu)) {
                    cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi();
                } else {
                    cap_nhat_canh_bao_chi_so_moi_vuot_nguong();
                }

            }
        });
        b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }


    public void cap_nhat_canh_bao_chi_so_moi_vuot_nguong() {

        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Lượng tiêu thụ vượt quá ngưỡng cảnh báo. Bạn có muốn tiếp tục");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi();
            }
        });
        b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }

    public void cap_nhat_luong_tieu_thu_nho_hon_khong_dh_quay_vong(final Integer luongtieuthu, final Integer ms_dh, final Integer ms_mnoi, final Integer chisocu, final Integer chisomoi) {
        //updateChiSoMoi(luongtieuthu, false);

        if(isConnected()) {
            edttieuthu.setText(String.valueOf(luongtieuthu));

            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoActivity.this);
            b.setTitle("Cảnh báo");
            b.setIcon(R.mipmap.ic_alert);
            b.setMessage("CSM nhỏ hơn CSC - Đồng hồ quay vòng! Có phải bạn muốn lưu chỉ số mới: " + chisomoi + ". Lượng tiêu thụ: " + luongtieuthu);
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!canhbaochenhlech(luongtieuthu)) {
                        cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi();
                    } else {
                        cap_nhat_canh_bao_chi_so_moi_vuot_nguong();
                    }

                    BarcodeReaderApiManager.getInstance().waterApi().GetWaterMeterResetNumber(ms_dh).enqueue(new Callback<ThongTinDaoSo>() {
                        @Override
                        public void onResponse(Call<ThongTinDaoSo> call, retrofit2.Response<ThongTinDaoSo> response) {
                            Integer so_lan_dao_so = null;
                            if (response.code() == 404) {
                                so_lan_dao_so = 1;
                            } else {

                                so_lan_dao_so = response.body().getSo_lan_dao_so();
                                if (so_lan_dao_so == null) {
                                    so_lan_dao_so = 1;

                                } else {
                                    so_lan_dao_so = so_lan_dao_so + 1;
                                }
                            }
                            //Insert lịch sử đảo số
                            //Integer ms_dh, Integer ms_mnoi , Integer chisodh, Integer solandaoso
                            insertMeterResetHis(ms_dh, ms_mnoi, chisomoi, so_lan_dao_so);


                        }

                        @Override
                        public void onFailure(Call<ThongTinDaoSo> call, Throwable t) {
                            Log.i("insertMeterResetHis", "update user pwd err : " + t.getMessage());
                        }
                    });

                }
            });
            b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = b.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(Color.WHITE);
            nbutton.setBackgroundResource(R.drawable.button_style);
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(Color.WHITE);
            pbutton.setBackgroundResource(R.drawable.button_style);
        }else {
            Toast.makeText(getApplicationContext(), "Không có kết nối internet! Không cập nhật chỉ số quay vòng", Toast.LENGTH_LONG).show();
        }
    }

    private void insertMeterResetHis(Integer ms_dh, Integer ms_mnoi , Integer chisodh, Integer solandaoso){


        MeterResetHis request = new MeterResetHis(ms_dh,ms_mnoi,chisodh,solandaoso);
        BarcodeReaderApiManager.getInstance().waterApi().insertMeterResetHistory(request).enqueue(new Callback<BarcodeResponse>() {

            @Override
            public void onResponse(Call<BarcodeResponse> call, retrofit2.Response<BarcodeResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(NhapSoActivity.this, "Cập nhật lịch sử đồng hồ thành công!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(NhapSoActivity.this, "Cập nhật lịch sử đồng hồ ko thành công!", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                Log.i("MeterResetHis", "update user pwd err : " +t.getMessage());

            }
        });



    }


    /* ms_mnoi	double
        ms_tk	double
        chiSoMoi	double
        soTieuThu	double
        readNewDate	string
        hasAmountFlag		boolean
        ms_ttrang	double
        urlImage	string
        */

    public void updateChiSoMoi(final Integer luongtieuthu, final boolean khongdocduoc) {

        if (isConnected()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                    byte[] ba = bao.toByteArray();
                    ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    Integer chisomoi = null;
                    Integer luongtieuthu1 = luongtieuthu;
                    boolean hasAmountFlag = true;
                    if (!khongdocduoc) {
                        chisomoi = Integer.parseInt(edtSomoi.getText().toString().trim());
                        hasAmountFlag = true;

                    } else {
                        hasAmountFlag = false;
                        chisomoi = null;
                        luongtieuthu1 = null;


                        //ba1 = null;
                    }
                    int chisocu = Integer.parseInt(edtSocu.getText().toString());
                    int ms_mnoi = Integer.parseInt(edtdanhba.getText().toString());
                    int ms_tk1 = Integer.parseInt(ms_tk);
                    String readNewDate = edtngaydocmoi.getText().toString();
                    String ghi_chu = edtghichu.getText().toString();
                    //int ms_ttrang = makhongdocduoc;
                   // MeterUpdateRequest request = new MeterUpdateRequest(ms_mnoi, ms_tk1, chisomoi, luongtieuthu1, 1, true, ba1, ghi_chu);
                    //updateMeter(ms_mnoi, request);

                }
            }.execute();

        } else {

            Toast.makeText(getApplicationContext(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();

        }

    }

    private void updateMeter(int id, MeterUpdateRequest request) {
        BarcodeReaderApiManager.getInstance().waterApi().updateMeter(id, request).enqueue(new Callback<BarcodeResponse>() {
            @Override
            public void onResponse(Call<BarcodeResponse> call, retrofit2.Response<BarcodeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NhapSoActivity.this, TabActivity.class);
                    startActivity(intent);
                } else {

                }
            }

            @Override
            public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                Log.i("Meter", "update meter err : " + t.getMessage());

            }
        });


    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.chuadoc) {
            Intent intent = new Intent(NhapSoActivity.this, Login.class);
            intent.putExtra("ms_tuyen", ms_tuyendoc);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            startActivity(intent);

           // new IntentIntegrator(NhapSoActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
        } else if (item.getItemId() == R.id.danhbadiemdung) {
            Intent intent = new Intent(NhapSoActivity.this, TabActivity.class);
            intent.putExtra("ms_tuyen", ms_tuyendoc);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            //  intent.putExtra("tongsodiemdung",String.valueOf(tongsoluong) );
            startActivity(intent);

        } else if (item.getItemId() == R.id.tuyendoc) {
            Intent intent = new Intent(NhapSoActivity.this, TuyenDoc.class);
            intent.putExtra("ms_tuyen", ms_tuyendoc);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            //  intent.putExtra("tongsodiemdung",String.valueOf(tongsoluong) );
            startActivity(intent);

        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void showErrorDialogue(final Integer resultscan) {
        Toast.makeText(this, "Mã danh bạ không tồn tại!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(NhapSoActivity.this, TabActivity.class);
        startActivity(intent);
    }

    public void capnhatsomoiDBTam(int luongtieuthu) {
        int ms_tuyen = Integer.parseInt(ms_tuyendoc);
        int ms_mnoi = Integer.parseInt(edtdanhba.getText().toString());
        String ten_kh = edttenkh.getText().toString();
        String dia_chi = edtdiachi.getText().toString();
        int ms_tk1 = Integer.parseInt(ms_tk);
        int ms_ttrang = 1;
        int chi_so_cu = Integer.parseInt(edtSocu.getText().toString());
        int chi_so_moi = Integer.parseInt(edtSomoi.getText().toString());
        String ngay_doc_cu = edtngaydoccu.getText().toString();
        String ngay_doc_moi = edtngaydocmoi.getText().toString();
        //int so_tthu = chi_so_moi - chi_so_cu;
        DongHoNoi dh = new DongHoNoi(ms_mnoi, ms_tuyen, ten_kh, dia_chi, ms_tk1, ms_ttrang, chi_so_cu, chi_so_moi, ngay_doc_cu, ngay_doc_moi, luongtieuthu);
        try {
            dbManager.nhapso(dh);
            //edttieuthu.setText(String.valueOf(luongtieuthu));
        } catch (SQLiteException e) {
            AlertDialog.Builder b = new AlertDialog.Builder(NhapSoActivity.this);
            b.setTitle("Xác nhận");
            b.setMessage("Lỗi trong quá trình lưu vào bảng tạm");
            b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    edtSomoi.clearFocus();
                }
            });
            b.create().show();
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        Log.d("SQLite", "Insert ok");

    }

    public void getThongTinKhachHang(Integer madb) {

        if (isConnected()) {
            String url = common.URL_API + "/GetCustomerInfo?ms_mnoi=" + madb + "&ms_tk=" + ms_tk + "&ms_tuyen=" + ms_tuyendoc;
            Log.d("GetCustomerInfo", url);
            new HttpAsyncTaskGetCustomerInfo().execute(url);
        } else {
            Toast.makeText(this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();

        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void capnhatDiemDungMatMang(Integer luongtieuthu) {

        String textnguyennhan = "";
        String dien_thoai = "";

        //quyet mã vạch so_thu luôn khác null
        String so_tthu = null;
        if (luongtieuthu != null) {
            so_tthu = String.valueOf(luongtieuthu);
        }
        String chi_so_moi = null;
        if (!"".equals(edtdanhba.getText().toString())) {
            int ms_moinoi = Integer.parseInt(edtdanhba.getText().toString());
            String tenkh = edttenkh.getText().toString();
            String diachi = edtdiachi.getText().toString();
            int ms_tk1 = Integer.parseInt(ms_tk);
            int ms_tt = makhongdocduoc;
            String chi_so_cu = edtSocu.getText().toString();
            if (!"".equals(edtSomoi.getText().toString())) {
                chi_so_moi = edtSomoi.getText().toString();
            }
            String ngay_doc_cu = edtngaydoccu.getText().toString();
            String ngay_doc_moi = edtngaydocmoi.getText().toString();
            //int so_tthu = chi_so_moi - chi_so_cu;

            int co_chi_so_moi = 1;

            int ms_tuyen = Integer.parseInt(ms_tuyendoc);

            byte[] anh = null;
            if (null != imgPhoto.getDrawable()) {
                anh = cmfuntion.ConverttoArrayByte(imgPhoto);
            }

            String tt3t = txtTT3T.getText().toString();
            String tttb = txtTTTB.getText().toString();
            String seri_dh = edtsodongho1.getText().toString();
            String mdsd = edtmdsd.getText().toString();
            dien_thoai = edtSDT.getText().toString();
            int ma_nn = makhongdocduoc;
            String ghi_chu = edtghichu.getText().toString();

            DiemDungMatMang ddmm = new DiemDungMatMang(ms_moinoi, tenkh, diachi, ms_tk1, 1, chi_so_cu, chi_so_moi,
                    ngay_doc_cu, ngay_doc_moi, so_tthu, ms_tuyen, co_chi_so_moi, tt3t, tttb, seri_dh, mdsd, 1, anh,"",dien_thoai,0, ghi_chu);

            int ketqua = dbManager.ThemDiemDungMatMang(ddmm);

            if (ketqua == 1) {
                Toast.makeText(getBaseContext(), "Cập nhật bảng tạm thành công!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getBaseContext(), "Lỗi cập nhật bảng tạm!", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getBaseContext(), "Danh bạ điểm dùng không được để trống", Toast.LENGTH_LONG).show();
        }

    }

    public boolean canhbaochenhlech(int luongtieuthu) {
        int tyledanhgia = config.getInt("tyledanhgia", 0);
        int TLDG = config.getInt("TLDG", 0);
        int CLTD = config.getInt("CLTD", 0);
        int trungbinh = 0;
        if("".equals(txtTTTB.getText().toString())||"null".equals(txtTTTB.getText().toString())) {
            trungbinh = 0;
        }else {
            trungbinh = Integer.parseInt(txtTTTB.getText().toString());
        }
        int giatrituyetdoi = Math.abs(luongtieuthu - trungbinh);
        if (tyledanhgia == 1) {
            if (giatrituyetdoi > (TLDG * trungbinh / 100)) {
                return true;
            }
        } else {
            if (giatrituyetdoi > CLTD) {
                return true;
            }
        }

        return false;
    }

    public void GetTopNInvoice(Integer ms_mnoi) {
        if (isConnected()) {
            String url = common.URL_API + "/GetTopNInvoice?ms_mnoi=" + ms_mnoi + "&topNo=3";
            new HttpAsyncTaskGetTopNInvoice().execute(url);
        } else {
            Toast.makeText(this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }

    }

    public void GetPurposeByMoinoi(Integer ms_mnoi) {
        if (isConnected()) {
            String url = common.URL_API + "/GetPurposeByMoinoi?ms_mnoi=" + ms_mnoi;
            Log.d("GetPurposeByMoinoi", url);
            new HttpAsyncTaskGetPurposeByMoinoi().execute(url);
        } else {
            Toast.makeText(this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }
    }


    private class HttpAsyncTaskGetCustomerInfo extends AsyncTask<String, JSONObject, Void> {
        String ms_tuyen = "";
        String mo_ta_tuyen = "";
        String ms_mnoi = "";
        String nguoi_thue = "";
        String dia_chi_mnoi = "";
        String chi_so_cu = "";
        String mo_ta_dong = "";
        String ngay_doc_cu = "";
        String dien_thoai = "";
        String so_seri ="";
        String chi_so_moi=null;
        String so_tthu=null;
        String ngay_doc_moi = null;
        JSONObject jsonKH;
        JSONArray jsonArray = new JSONArray();
        ProgressDialog progressDialog = new ProgressDialog(NhapSoActivity.this);
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Nạp dữ liệu...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];


            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                if (jsonArray.length()>0) {
                    jsonKH = jsonArray.getJSONObject(0);
                    ms_tuyen = jsonKH.getString("ms_tuyen");
                    mo_ta_tuyen = jsonKH.getString("mo_ta_tuyen");
                    ms_mnoi = jsonKH.getString("ms_mnoi");
                    nguoi_thue = jsonKH.getString("nguoi_thue");
                    dia_chi_mnoi = jsonKH.getString("dia_chi_mnoi");
                    chi_so_cu = jsonKH.getString("chi_so_cu");

                    mo_ta_dong = jsonKH.getString("mo_ta_dong");
                    ngay_doc_cu = jsonKH.getString("ngay_doc_cu");
                    dien_thoai =  jsonKH.getString("dien_thoai");
                    savesdttemp =  dien_thoai;
                    so_seri = jsonKH.getString("so_seri");

                    chi_so_moi = jsonKH.getString("chi_so_moi");
                    so_tthu  = jsonKH.getString("so_tieu_thu");
                    ngay_doc_moi = jsonKH.getString("ngay_doc_moi");
                    so_tthu_cu = jsonKH.getInt("so_tthu_cu");

                    if(null!= chi_so_moi && null != so_tthu && null!= ngay_doc_moi){

                    }
                } else {
                    Intent intent = new Intent(NhapSoActivity.this, TabActivity.class);
                    intent.putExtra("ms_tuyen", ms_tuyendoc);
                    intent.putExtra("ms_tk", ms_tk);
                    intent.putExtra("ms_bd", ms_bd);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (jsonArray.length()>0) {
                    tvTenTuyen.setText("Tuyến: " + common.cat2kitucuoi(ms_tuyen) + " - " + mo_ta_tuyen);
                    edtdanhba.setText(common.cat2kitucuoi(ms_mnoi));
                    edttenkh.setText(String.valueOf(nguoi_thue));
                    edtdiachi.setText(String.valueOf(dia_chi_mnoi));
                    edtSocu.setText(common.cat2kitucuoi(chi_so_cu));
                    edtsodongho1.setText(so_seri);

                    // edtmdsd.setText(mo_ta_dong);
                    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    //Date ngaydoccu = (Date) sdf.parse();

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = format1.parse(common.cat10kitucuoi(ngay_doc_cu));
                    String ngaydoccu = format2.format(date);

                    edtngaydoccu.setText(ngaydoccu);
                    edtngaydocmoi.setText(format2.format(new Date()).toString());
                    edtSDT.setText(dien_thoai);

                    if(!"null".equals(chi_so_moi) &&!"null".equals(so_tthu) && null!= ngay_doc_moi){
                        edtSomoi.setText(common.cat2kitucuoi(chi_so_moi));
                        edttieuthu.setText(common.cat2kitucuoi(so_tthu));
                        Date date1 = format1.parse(common.cat10kitucuoi(ngay_doc_moi));
                        String ngay_doc_moi = format2.format(date1);
                        edtngaydocmoi.setText(ngay_doc_moi);
                        edtSomoi.setEnabled(false);
                        edttieuthu.setEnabled(false);
                        btnLuu.setEnabled(false);
                        edtghichu.setEnabled(false);
                    }
                }else {
                    showErrorDialogue(ms_db);
                   // return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    private class HttpAsyncTaskGetTopNInvoice extends AsyncTask<String, JSONObject, Void> {

        JSONArray jsonArrayTT3T = new JSONArray();
        String strTemp = "";
        int[] stt3t = new int[3];
        int stttong = 0;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];

            try {
                jsonArrayTT3T = ReadJson.readJSonArrayFromURL(url);
                if (jsonArrayTT3T.length() > 0) {
                    for (int i = 0; i < jsonArrayTT3T.length(); i++) {
                        JSONObject objTinhTrang = jsonArrayTT3T.getJSONObject(i);
                        int stt = objTinhTrang.getInt("so_tieu_thu");
                        stt3t[i] = stt;
                        strTemp += String.valueOf(stt) + "-";
                    }
                    if (strTemp.length() > 0) {
                        strTemp = strTemp.substring(0, strTemp.length() - 1);
                    }

                    for (int i = 0; i < stt3t.length; i++) {
                        stttong += stt3t[i];
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            txtTT3T.setText(strTemp);
            if (jsonArrayTT3T.length() > 0) {
                txtTTTB.setText(String.valueOf(stttong / jsonArrayTT3T.length()));
            }
            super.onPostExecute(result);
        }
    }

    private class HttpAsyncTaskGetPurposeByMoinoi extends AsyncTask<String, JSONObject, Void> {

        String mdsd = "";

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArrMDSD;
            String temp = "";
            int countmdkd = 0;

            try {
                jsonArrMDSD = ReadJson.readJSonArrayFromURL(url);
                if (jsonArrMDSD.length() > 1) {
                    for (int i = 0; i < jsonArrMDSD.length(); i++) {
                        JSONObject objMDSD = jsonArrMDSD.getJSONObject(i);
                        String mo_ta_dong = objMDSD.getString("mo_ta_dong");
                        if( i == jsonArrMDSD.length() -1) {
                            temp += mo_ta_dong;
                        }else {
                            temp += mo_ta_dong + " + ";
                        }
                        countmdkd +=1;
                    }
                }else if(jsonArrMDSD.length() == 1){
                    JSONObject objMDSD = jsonArrMDSD.getJSONObject(0);{
                        String mo_ta_dong = objMDSD.getString("mo_ta_dong");
                        String mo_ta_dong_vuot = objMDSD.getString("mo_ta_dong_vuot");
                        if(!"null".equals(mo_ta_dong_vuot)&& !"null".equals(mo_ta_dong)){
                            temp = mo_ta_dong + " + "+ mo_ta_dong_vuot;
                            countmdkd = 2;
                        }else {
                            temp =  mo_ta_dong;
                            countmdkd = 1;
                        }
                    }
                }
                mdsd = "(" + countmdkd + ")" + temp;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            edtmdsd.setText(mdsd);
            super.onPostExecute(result);
        }
    }

}
