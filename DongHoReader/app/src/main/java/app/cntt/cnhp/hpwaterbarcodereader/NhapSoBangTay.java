package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import api.BarcodeReaderApiManager;
import model.BarcodeResponse;
import model.DiemDungMatMang;
import model.DongHoKhoi;
import model.DongHoNoi;
import model.ThongTinTieuThu;
import model.TinhtrangDocDongHo;
import model.request.CustomerInfoChange;
import model.request.MeterResetHis;
import model.request.MeterUpdateKhongDocDuocRequest;
import model.request.MeterUpdateRequest;
import model.request.UpdatePhoneNumber;
import model.request.UpdateUserPwd;
import model.response.ThongTinDaoSo;
import model.response.ThongTinDongHo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CommonFuntions;
import utils.CommonText;
import utils.DBManager;
import utils.ReadJson;
import utils.SharedPref;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

public class NhapSoBangTay extends Activity {
    TextView tvTenTuyen;
    TextView tvNDC1;
    TextView tvNDM1;
    TextView tvSC1;
    TextView tvSM1;
    TextView tvNDC2;
    TextView tvNDM2;
    TextView tvSC2;
    TextView tvSM2;




    EditText edtdanhba;
    EditText edttendh;
    EditText edtdiachi;
    EditText edtSocu1;
    EditText edtSomoi1;
    EditText edttieuthu1;
    EditText edtngaydoccu1;
    EditText edtngaydocmoi1;

    EditText edtSocu2;
    EditText edtSomoi2;
    EditText edttieuthu2;
    EditText edtngaydoccu2;
    EditText edtngaydocmoi2;

    EditText edtmdsd;
    EditText edtseridh;
    EditText edtghichu;
    Button btnLuuCS1;
    Button btnLuuCS2;
    //Button btntruoc;
    //Button btnsau;
    DBManager dbManager;
    //Spinner spinkhongdocduoc;
    CheckBox chkkhongdocduoc;
    CheckBox chkdoccs1;
    CheckBox chkdoccs2;
    Integer khongdocduocmatmang = 0;

    //bien toan cuc
    String ms_tk = "";
    String ms_nhom = "";
    String ms_bd;
    Integer ms_db;
    boolean chkcachdoc;
    SharedPref config;
    int makhongdocduoc = 1;
    DongHoKhoi ttkh;
    Integer luongtieuthu1 = null;
    Integer luongtieuthu2 = null;
    Integer chisomoi1 = null;
    Integer chisocu1 = null;
    Integer chisomoi2 = null;
    Integer chisocu2 = null;
    Integer ms_dhk = null;
    Integer Ms_DH = null;
    Integer So_TThu_Cu1 = 0;
    Integer So_TThu_Cu2 = 0;
    Integer He_So = 0;
    Integer Kha_Nang_DH = 0;
    CommonText common = new CommonText();
    CommonFuntions cmfuntion;
    ArrayList<TinhtrangDocDongHo> listTinhTrang;
    //end

    // khái báo biến image upload
    private ImageButton btnPhoto;
    ImageView imgPhoto;
    Bitmap selectedBitmap;
    String ba1;

    //end

    private Uri photoUri;
    private final static int TAKE_PHOTO = 1;
    private final static String PHOTO_URI = "photoUri";
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_so_bang_tay);

        Intent intent = getIntent();
        ttkh = (DongHoKhoi) intent.getSerializableExtra("ttct");
        final ArrayList<DongHoKhoi> arrTTCT = (ArrayList<DongHoKhoi>) intent.getSerializableExtra("arrlist");
        ms_db = ttkh.getMs_dhk();
        ms_dhk = Integer.valueOf(ttkh.getMs_dhk());
        config = new SharedPref(this);
        ms_tk = config.getString("ms_tk", "252");
        ms_bd = config.getString("ms_bd", "");
        ms_nhom = config.getString("ms_nhom", "");
        cmfuntion = new CommonFuntions();


        khaibaobien();
        getThongTinKhachHang(ms_dhk);

        //disableGhiChiSo1();
        //disableGhiChiSo2();
        common.getDHKLQOfNhom(ttkh.getMs_bd(), Integer.parseInt(ms_nhom));

        if(ttkh.getDocdh().equals(1)){
            disableGhiChiSo1();
            enableGhiChiSo2();
        }else if (ttkh.getDocdh().equals(2)){
            enableGhiChiSo1();
            disableGhiChiSo2();
        }else if(ttkh.getDocdh().equals(3)){
            enableGhiChiSo1();
            enableGhiChiSo2();
        }
        /*if(ttkh.getMs_nhom().equals(Integer.parseInt(ms_nhom))){
            disableGhiChiSo1();
            enableGhiChiSo2();
        }
        for(int i= 0; i< common.lstDhLienQuan.size(); i++){
            if(common.lstDhLienQuan.get(i).getMs_dhk_lq().equals(ttkh.getMs_dhk())){
               enableGhiChiSo1();
            }


        }*/
        edtSomoi1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!"".equals(edtSomoi1.getText().toString()) && !"".equals(edtSocu1.getText().toString())) {
                    luongtieuthu1 = tinhLuongTieuThu1(Integer.parseInt(edtSomoi1.getText().toString()), Integer.parseInt(edtSocu1.getText().toString()), So_TThu_Cu1);
                } else {
                    edttieuthu1.setText("");
                }
            }
        });

        edtSomoi2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!"".equals(edtSomoi2.getText().toString()) && !"".equals(edtSocu2.getText().toString())) {
                    luongtieuthu2 = tinhLuongTieuThu2(Integer.parseInt(edtSomoi2.getText().toString()), Integer.parseInt(edtSocu2.getText().toString()), So_TThu_Cu2);
                } else {
                    edttieuthu2.setText("");
                }
            }
        });



        //Chức năng upload iamge
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
                   photoUri = getContentResolver().insert(EXTERNAL_CONTENT_URI, new ContentValues());
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


        chkkhongdocduoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkkhongdocduoc.isChecked()) {
                    disableGhiChiSo1();
                    disableGhiChiSo2();
                    btnLuuCS2.setEnabled(true);
                    btnLuuCS2.setBackgroundResource(R.drawable.button_style);


                }else {
                    if(ttkh.getDocdh().equals(1)){
                        enableGhiChiSo2();
                    }else if (ttkh.getDocdh().equals(2)){
                        enableGhiChiSo1();
                    }else if(ttkh.getDocdh().equals(3)){
                        enableGhiChiSo1();
                        enableGhiChiSo2();
                    }
                }
            }
        });

        btnLuuCS2.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                //if(checkGetCustomerInfoWereRead(ms_db, Integer.parseInt(ms_tk),  Integer.parseInt(ms_tuyendoc))) {

                //check giao diện trước
                if (chkkhongdocduoc.isChecked() == false) {
                    int loai_chi_so_btn = 2;

                    if (!"".equals(edtSomoi2.getText().toString())&&!"".equals(edtSocu2.getText().toString())) {
                        chisomoi2 = Integer.parseInt(edtSomoi2.getText().toString());
                        chisocu2 = Integer.parseInt(edtSocu2.getText().toString());
                        luongtieuthu2 = tinhLuongTieuThu2(chisomoi2, chisocu2, So_TThu_Cu2);

                        int tieuthucanhbao = 0;
                        if (chisomoi2 >= chisocu2) {
                            tieuthucanhbao = (chisomoi2 - chisocu2) * He_So + So_TThu_Cu2;
                        } else {
                            tieuthucanhbao = (chisomoi2 + Kha_Nang_DH - chisocu2 + 1) * He_So + So_TThu_Cu2;
                        }
                        if (tieuthucanhbao != luongtieuthu2 || chisomoi2 > Kha_Nang_DH) {
                            String msg = "";
                            if (chisomoi2 >= chisocu2) {
                                msg = "Lượng tiêu thụ: " + tieuthucanhbao + "=(" + chisomoi2 + " - " + chisocu2 + ")* " + He_So + " + " + So_TThu_Cu2 + ")" + ". Lượng tiêu thụ hiển thị: " + luongtieuthu2 + " => Sai. Vui lòng kiểm tra lại kết nối, nhập lại chỉ số mới và tính lại số tiêu thụ!";
                            } else {
                                msg = "Lượng tiêu thụ: " + tieuthucanhbao + "=(" + chisomoi2 + "+" + Kha_Nang_DH + " - " + chisocu2 + ")* " + He_So + " + " + So_TThu_Cu2 + ")" + ". Lượng tiêu thụ hiển thị: " + luongtieuthu2 + " => Sai. Vui lòng kiểm tra kết nối, nhập lại chỉ số mới và tính lại số tiêu thụ!";
                            }
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NhapSoBangTay.this);
                            alertDialogBuilder.setTitle("Cảnh báo");
                            alertDialogBuilder.setMessage(msg);
                            alertDialogBuilder.setIcon(R.mipmap.ic_alert);
                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("ĐỒNG Ý",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Toast.makeText(NhapSoBangTay.this, "Nhập lại chỉ số mới và số tiêu thụ!", Toast.LENGTH_LONG).show();

                                                }
                                            })
                                    .setNegativeButton("HỦY",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            nbutton.setTextColor(Color.WHITE);
                            nbutton.setBackgroundResource(R.drawable.button_style);
                            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            pbutton.setTextColor(Color.WHITE);
                            pbutton.setBackgroundResource(R.drawable.button_style);
                        }else {
                            if (null != luongtieuthu2 && !"".equals(edttieuthu2.getText().toString())) {
                                if ((chisomoi2 > chisocu2) || ((luongtieuthu2 !=0) && (chisomoi2.equals(chisocu2)))) {

                                    cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi(chisomoi2,luongtieuthu2,loai_chi_so_btn);

                                }else if (luongtieuthu2 == 0) {
                                    canh_bao_cap_nhat_luong_tieu_thu_bang_khong(chisomoi2,luongtieuthu2,loai_chi_so_btn);

                                } else {

                                    cap_nhat_luong_tieu_thu_nho_hon_khong_dh_quay_vong(luongtieuthu2,Ms_DH, ms_db, chisocu2, chisomoi2, loai_chi_so_btn);
                                }
                            }else {
                                edttieuthu2.setEnabled(false);
                                Toast.makeText(NhapSoBangTay.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        Toast.makeText(NhapSoBangTay.this, "Chưa nhập CSM(hoặc không tồn tại CSC)", Toast.LENGTH_LONG).show();
                    }


                }else {
                    if (coNguyenNhanVaAnh()) {
                        cap_nhat_dd_khong_doc_duoc();
                    } else {
                        canh_bao_khong_co_nguyen_nhan_va_anh();
                    }
                }

            }


        });

        btnLuuCS1.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                //if(checkGetCustomerInfoWereRead(ms_db, Integer.parseInt(ms_tk),  Integer.parseInt(ms_tuyendoc))) {

                //check giao diện trước
                if (chkkhongdocduoc.isChecked() == false) {
                    int loai_chi_so_btn = 1;

                    if (!"".equals(edtSomoi1.getText().toString())&&!"".equals(edtSocu1.getText().toString())) {
                        chisomoi1 = Integer.parseInt(edtSomoi1.getText().toString());
                        chisocu1 = Integer.parseInt(edtSocu1.getText().toString());
                        luongtieuthu1 = tinhLuongTieuThu2(chisomoi1, chisocu1, So_TThu_Cu1);

                        int tieuthucanhbao = 0;
                        if (chisomoi1 >= chisocu1) {
                            tieuthucanhbao = (chisomoi1 - chisocu1) * He_So + So_TThu_Cu1;
                        } else {
                            tieuthucanhbao = (chisomoi1 + Kha_Nang_DH - chisocu1 + 1) * He_So + So_TThu_Cu1;
                        }
                        if (tieuthucanhbao != luongtieuthu1 || chisomoi1 > Kha_Nang_DH) {
                            String msg = "";
                            if (chisomoi1 >= chisocu1) {
                                msg = "Lượng tiêu thụ: " + tieuthucanhbao + "=(" + chisomoi1 + " - " + chisocu1 + ")* " + He_So + " + " + So_TThu_Cu1 + ")" + ". Lượng tiêu thụ hiển thị: " + luongtieuthu1 + " => Sai. Vui lòng kiểm tra lại kết nối, nhập lại chỉ số mới và tính lại số tiêu thụ!";
                            } else {
                                msg = "Lượng tiêu thụ: " + tieuthucanhbao + "=(" + chisomoi1 + "+" + Kha_Nang_DH + " - " + chisocu1 + ")* " + He_So + " + " + So_TThu_Cu1 + ")" + ". Lượng tiêu thụ hiển thị: " + luongtieuthu1 + " => Sai. Vui lòng kiểm tra kết nối, nhập lại chỉ số mới và tính lại số tiêu thụ!";
                            }
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NhapSoBangTay.this);
                            alertDialogBuilder.setTitle("Cảnh báo");
                            alertDialogBuilder.setMessage(msg);
                            alertDialogBuilder.setIcon(R.mipmap.ic_alert);
                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("ĐỒNG Ý",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Toast.makeText(NhapSoBangTay.this, "Nhập lại chỉ số mới và số tiêu thụ!", Toast.LENGTH_LONG).show();

                                                }
                                            })
                                    .setNegativeButton("HỦY",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            nbutton.setTextColor(Color.WHITE);
                            nbutton.setBackgroundResource(R.drawable.button_style);
                            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            pbutton.setTextColor(Color.WHITE);
                            pbutton.setBackgroundResource(R.drawable.button_style);
                        }else {
                            if (null != luongtieuthu1 && !"".equals(edttieuthu1.getText().toString())) {
                                if ((chisomoi1 > chisocu1) || ((luongtieuthu1 !=0) && (chisomoi1.equals(chisocu1)))) {

                                    cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi(chisomoi1,luongtieuthu1,loai_chi_so_btn);

                                }else if (luongtieuthu1 == 0) {
                                    canh_bao_cap_nhat_luong_tieu_thu_bang_khong(chisomoi1,luongtieuthu1,loai_chi_so_btn);

                                } else {

                                    cap_nhat_luong_tieu_thu_nho_hon_khong_dh_quay_vong(luongtieuthu1,Ms_DH, ms_db, chisocu1, chisomoi1, loai_chi_so_btn);
                                }
                            }else {
                                edttieuthu2.setEnabled(false);
                                Toast.makeText(NhapSoBangTay.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        Toast.makeText(NhapSoBangTay.this, "Chưa nhập CSM(hoặc không tồn tại CSC!)", Toast.LENGTH_LONG).show();
                    }


                }else {
                    if (coNguyenNhanVaAnh()) {
                        cap_nhat_dd_khong_doc_duoc();
                    } else {
                        canh_bao_khong_co_nguyen_nhan_va_anh();
                    }
                }

            }


        });





    }

      //phien ban vsmart
    @Override
   protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTO_URI, photoUri);
    }
     //end vsmart


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), TabActivity.class);
        //intent.putExtra("ms_tuyen", ms_tuyendoc);
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
                       // selectedBitmap = getResizedBitmap((Bitmap) data.getExtras().get("data"), 400);

                        //Phiên bản vsmart
                        try {
                            InputStream stream = getContentResolver().openInputStream(photoUri);
                            selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 400);
                            imgPhoto.setImageBitmap(selectedBitmap);
                            int i = getContentResolver().delete(photoUri, null, null);
                            Log.e("getContentResolver:", ""+i);
                            stream.close();


                        } catch (FileNotFoundException e) {
                            Log.e("Error", "FileNotFound",e);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            //End phien ban Vsmart


                 //   }
               // }
            //}
        }

    }

    private Integer tinhLuongTieuThu1(final Integer chisomoi, final Integer chisocu, final Integer sotthucu1) {


        if (chisomoi < chisocu) {
            luongtieuthu1 = (chisomoi + Kha_Nang_DH - chisocu + 1) * He_So + sotthucu1;
        } else {
            luongtieuthu1 = (chisomoi - chisocu) * He_So + sotthucu1;
        }
        edttieuthu1.setText(String.valueOf(luongtieuthu1));
        return luongtieuthu1;


    }

    private Integer tinhLuongTieuThu2(final Integer chisomoi, final Integer chisocu, final Integer sotthucu2) {


        if (chisomoi < chisocu) {
            luongtieuthu2 = (chisomoi + Kha_Nang_DH - chisocu + 1) * He_So + sotthucu2;
        } else {
            luongtieuthu2 = (chisomoi - chisocu) * He_So + sotthucu2;
        }
        edttieuthu2.setText(String.valueOf(luongtieuthu2));
        return luongtieuthu2;


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
    public void writeBitmapToMemory(File file, Bitmap bitmap) {
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();


        }

    }

    // end funtion image upoad

    public void khaibaobien() {

        tvTenTuyen = (TextView) findViewById(R.id.tentuyenbt);

        tvNDC1 = (TextView) findViewById(R.id.tvNDC1);
        tvNDM1 = (TextView) findViewById(R.id.tvNDM1);
        tvSC1 = (TextView) findViewById(R.id.tvSC1);
        tvSM1 = (TextView) findViewById(R.id.tvSM1);
        tvNDC2 = (TextView) findViewById(R.id.tvNDC2);
        tvNDM2 = (TextView) findViewById(R.id.tvNDM2);
        tvSC2 = (TextView) findViewById(R.id.tvSC2);
        tvSM2 = (TextView) findViewById(R.id.tvSM2);

        edtdanhba = (EditText) findViewById(R.id.edtdanhbabt);
        edttendh = (EditText) findViewById(R.id.edttendhbt);
        edtdiachi = (EditText) findViewById(R.id.edtDiaChibt);

        edtSocu1 = (EditText) findViewById(R.id.edtSocubt1);
        edtSomoi1 = (EditText) findViewById(R.id.edtSomoibt1);
        edttieuthu1 = (EditText) findViewById(R.id.edttieuthubt1);
        edtngaydoccu1 = (EditText) findViewById(R.id.edtngaydoccubt1);
        edtngaydocmoi1 = (EditText) findViewById(R.id.edtngaydocmoibt1);

        edtSocu2 = (EditText) findViewById(R.id.edtSocubt2);
        edtSomoi2 = (EditText) findViewById(R.id.edtSomoibt2);
        edttieuthu2 = (EditText) findViewById(R.id.edttieuthubt2);
        edtngaydoccu2 = (EditText) findViewById(R.id.edtngaydoccubt2);
        edtngaydocmoi2 = (EditText) findViewById(R.id.edtngaydocmoibt2);

        edtSomoi1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        edtSomoi2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        edttieuthu1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        edttieuthu2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

        edtseridh = (EditText) findViewById(R.id.edtsodonghobt);

        btnLuuCS1 = (Button) findViewById(R.id.btnLuuCS1);
        btnLuuCS2 = (Button) findViewById(R.id.btnLuuCS2);

        edtghichu = (EditText) findViewById(R.id.edtghichu);
        chkkhongdocduoc = (CheckBox) findViewById(R.id.chkcachdoc);
        chkdoccs1= (CheckBox) findViewById(R.id.chkdoccs1);
        chkdoccs2= (CheckBox) findViewById(R.id.chkdoccs2);

        //spinkhongdocduoc = (Spinner) findViewById(R.id.spinkhongdocduoc);

        dbManager = new DBManager(this);

        chkkhongdocduoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkkhongdocduoc.isChecked()) {
                    khongdocduocmatmang = 1;
                    /*edtSomoi1.setText("");
                    edttieuthu.setText("");
                    edtSomoi.setEnabled(false);
                    edttieuthu.setEnabled(false);
                    spinkhongdocduoc.setEnabled(true);*/

                } else {
                    khongdocduocmatmang = 0;
                    //spinkhongdocduoc.setSelection(7);
                    //spinkhongdocduoc.setSelection(0);
                    //spinkhongdocduoc.setEnabled(false);
                    /*edtSomoi.setEnabled(true);
                    edttieuthu.setEnabled(true);*/
                }
            }
        });

        btnLuuCS1.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {


            }


        });

    }

    public void canh_bao_khong_co_nguyen_nhan_va_anh() {
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
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

    public void cap_nhat_dd_khong_doc_duoc() {
        if (isConnected()) {
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
            b.setTitle("Thông báo");
            b.setMessage("Bạn có muốn cập nhật điểm dùng không đọc được!");
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    capnhatDiemDungKhongDocDuoc();


                }
            });
            b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();


        }

    }
    public void cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi( final Integer chisomoi, final Integer luongtieuthu, final int loai_chi_so) {
        if (isConnected()) {

            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
            b.setTitle("Thông báo");
            b.setMessage("Bạn muốn cập nhật chỉ số mới: " + String.valueOf(chisomoi) + ", Lượng tiêu thụ: " + luongtieuthu);
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    updateChiSoMoi(luongtieuthu, loai_chi_so);
                    /*Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                    intent.putExtra("ms_nhom", ms_nhom);
                    intent.putExtra("ms_tk", ms_tk);
                    intent.putExtra("ms_bd", ms_bd);
                    startActivity(intent);*/


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
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
            b.setTitle("Cảnh báo");
            b.setMessage("Mất kết nối internet! Cập nhật điểm dùng vào bảng tạm");
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();


                }
            });
            b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();


        }
    }


    public void canh_bao_cap_nhat_luong_tieu_thu_bang_khong( final Integer chisomoi,  final Integer luongtieuthu, final int loai_chi_so) {
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Lượng tiêu thụ bằng 0. Bạn chắc chắn muốn nhập?");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if (!canhbaochenhlech(luongtieuthu)) {
                    cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi(chisomoi,luongtieuthu, loai_chi_so);
               // } else {
                //    cap_nhat_canh_bao_chi_so_moi_vuot_nguong();
               // }

            }
        });
        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }

    public void cap_nhat_luong_tieu_thu_nho_hon_khong_dh_quay_vong(final Integer luongtieuthu, final Integer ms_dh, final Integer ms_mnoi, final Integer chisocu, final Integer chisomoi, final int loai_chi_so) {
        //updateChiSoMoi(luongtieuthu, false);

        if(isConnected()) {
            edttieuthu2.setText(String.valueOf(luongtieuthu));

            LayoutInflater li = LayoutInflater.from(NhapSoBangTay.this);
            View promptsView = li.inflate(R.layout.alert_so_tieu_thu, null);
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
            b.setView(promptsView);
            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);
            b.setTitle("Cảnh báo");
            b.setIcon(R.mipmap.ic_alert);
            b.setMessage("CSM nhỏ hơn CSC - Đồng hồ quay vòng! Có phải bạn muốn lưu chỉ số mới: " + chisomoi + ". Lượng tiêu thụ: " + luongtieuthu + ". Nhập '1234' để xác nhận đồng hồ quay vòng");
            b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if ("1234".equals(userInput.getText().toString())) {
                        cap_nhat_co_anh_co_nguyen_nhan_co_chi_so_moi(chisomoi, luongtieuthu, loai_chi_so);
                    }


                }
            });
            b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            //b.create().show();
            AlertDialog alert = b.create();
            alert.show();
            Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(Color.WHITE);
            nbutton.setBackgroundResource(R.drawable.button_style);
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            //pbutton.setBackgroundColor(Color.RED);
            pbutton.setTextColor(Color.WHITE);
            pbutton.setBackgroundResource(R.drawable.button_style);

        }else {
            Toast.makeText(getApplicationContext(), "Không có kết nối internet! Không cập nhật chỉ số quay vòng", Toast.LENGTH_LONG).show();
        }
    }

    public void canh_bao_chi_so_moi_de_trong() {
        //Log.d("btnLuu:", "không checkbok- khong nhap chi so moi");
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapSoBangTay.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Bạn phải nhập chỉ số mới!");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //edtSomoi.requestFocus();

            }
        });
        b.create().show();
    }

    private void insertMeterResetHis(Integer ms_dh, Integer ms_mnoi, Integer chisodh, Integer solandaoso) {


        MeterResetHis request = new MeterResetHis(ms_dh, ms_mnoi, chisodh, solandaoso);
        BarcodeReaderApiManager.getInstance().waterApi().insertMeterResetHistory(request).enqueue(new Callback<BarcodeResponse>() {

            @Override
            public void onResponse(Call<BarcodeResponse> call, retrofit2.Response<BarcodeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NhapSoBangTay.this, "Cập nhật lịch sử đồng hồ thành công!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(NhapSoBangTay.this, "Cập nhật lịch sử đồng hồ ko thành công!", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                Log.i("MeterResetHis", "update user pwd err : " + t.getMessage());

            }
        });
    }


    /*
     *  ms_mnoi
        ms_tk
        chiSoMoi
        soTieuThu
        readNewDate - định dạng ngày tháng truyền xuống
        hasAmountFlag
        ms_ttrang
        Base64Image
    * */

    public void updateChiSoMoi(final Integer luongtieuthu, final  int loaichiso) {

        if (isConnected()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    Integer chisomoi = null;
                    if(loaichiso ==1 ){
                        chisomoi = Integer.parseInt(edtSomoi1.getText().toString().trim());

                    }else if(loaichiso ==2){
                        chisomoi = Integer.parseInt(edtSomoi2.getText().toString().trim());
                    }
                    int ms_dhk = Integer.parseInt(edtdanhba.getText().toString());
                    int ms_tk1 = Integer.parseInt(ms_tk);
                    String ghi_chu = edtghichu.getText().toString();
                    MeterUpdateRequest request = new MeterUpdateRequest(ms_dhk, ms_tk1, chisomoi, luongtieuthu, loaichiso,ghi_chu);
                    updateMeter(ms_dhk, request);

                }
            }.execute();

        } else {

            Toast.makeText(getApplicationContext(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();

        }

    }

    public void updateChiSoMoiKhongDocDuoc(final Integer luongtieuthu) {

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


                    int ms_dhk = Integer.parseInt(edtdanhba.getText().toString());
                    int ms_tk1 = Integer.parseInt(ms_tk);
                    String ghi_chu = edtghichu.getText().toString();
                    MeterUpdateKhongDocDuocRequest request = new MeterUpdateKhongDocDuocRequest(ms_dhk, ms_tk1, ghi_chu,ba1);
                    updateMeterKhongDocDuoc(ms_dhk, request);

                }
            }.execute();

        } else {

            Toast.makeText(getApplicationContext(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();

        }

    }
    public int flag = 0;
    private void updateMeter(int id, MeterUpdateRequest request) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        flag = 0;
        BarcodeReaderApiManager.getInstance().waterApi().updateMeter(id, request).enqueue(new Callback<BarcodeResponse>() {
            @Override
            public void onResponse(Call<BarcodeResponse> call, retrofit2.Response<BarcodeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NhapSoBangTay.this, TabActivity.class);
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

    private void updateMeterKhongDocDuoc(int id, MeterUpdateKhongDocDuocRequest request) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        flag = 0;
        BarcodeReaderApiManager.getInstance().waterApi().updateMeterKhongDocDuoc(id, request).enqueue(new Callback<BarcodeResponse>() {
            @Override
            public void onResponse(Call<BarcodeResponse> call, retrofit2.Response<BarcodeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NhapSoBangTay.this, TabActivity.class);
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


    public void getThongTinKhachHang(Integer ms_dhk) {

        if (isConnected()) {
            String url = common.URL_API + "/getdhkbyid?ms_dhk=" + ms_dhk;
            Log.d("getdhkbyid", url);
            new HttpAsyncTaskGetCustomerInfo().execute(url);
        } else {
            Toast.makeText(this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();

        }
    }


    public void showErrorDialogue(final Integer resultscan) {
        Toast.makeText(this, "Mã danh bạ không tồn tại!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(NhapSoBangTay.this, TabActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.chuadoc) {
            Intent intent = new Intent(NhapSoBangTay.this, Login.class);
            //intent.putExtra("ms_tuyen", ms_tuyendoc);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            startActivity(intent);

        } else if (item.getItemId() == R.id.danhbadiemdung) {
            Intent intent = new Intent(NhapSoBangTay.this, TabActivity.class);
            //intent.putExtra("ms_tuyen", ms_tuyendoc);
            intent.putExtra("ms_tk", ms_tk);
            intent.putExtra("ms_bd", ms_bd);
            //  intent.putExtra("tongsodiemdung",String.valueOf(tongsoluong) );
            startActivity(intent);

        } else if (item.getItemId() == R.id.tuyendoc) {
            Intent intent = new Intent(NhapSoBangTay.this, TuyenDoc.class);
            //intent.putExtra("ms_tuyen", ms_tuyendoc);
            intent.putExtra("ms_tk", ms_tk);
            String ms_bd1 = config.getString("app_username", "");
            intent.putExtra("ms_bd", ms_bd1);
            startActivity(intent);

        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public byte[] ConverttoArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }




    public boolean coNguyenNhanVaAnh() {
        //TinhtrangDocDongHo ttd = (TinhtrangDocDongHo) spinkhongdocduoc.getSelectedItem();

        if ( imgPhoto.getDrawable() == null) {
            //ttd.getId() == 1 ||
            //Log.d("savedFileDestination", savedFileDestination.getName());
            return false;
        }
        return true;

    }


    public void capnhatDiemDungKhongDocDuoc() {
        updateChiSoMoiKhongDocDuoc(luongtieuthu1);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void disableGhiChiSo1(){



        chkdoccs1.setEnabled(false);
        edtngaydoccu1.setEnabled(false);
        edtngaydocmoi1.setEnabled(false);
        edtSocu1.setEnabled(false);
        edtSomoi1.setEnabled(false);
        edttieuthu1.setEnabled(false);
        btnLuuCS1.setEnabled(false);

        chkdoccs1.setBackgroundColor(Color.GRAY);
        edtngaydoccu1.setBackgroundColor(Color.GRAY);
        edtngaydocmoi1.setBackgroundColor(Color.GRAY);
        edtSocu1.setBackgroundColor(Color.GRAY);
        edtSomoi1.setBackgroundColor(Color.GRAY);
        edttieuthu1.setBackgroundColor(Color.GRAY);
        btnLuuCS1.setBackgroundColor(Color.GRAY);

        tvNDC1.setBackgroundColor(Color.GRAY);
        tvNDM1.setBackgroundColor(Color.GRAY);
        tvSC1.setBackgroundColor(Color.GRAY);
        tvSM1.setBackgroundColor(Color.GRAY);


    }

    public void disableGhiChiSo2(){


        chkdoccs2.setEnabled(false);
        edtngaydoccu2.setEnabled(false);
        edtngaydocmoi2.setEnabled(false);
        edtSocu2.setEnabled(false);
        edtSomoi2.setEnabled(false);
        edttieuthu2.setEnabled(false);
        btnLuuCS2.setEnabled(false);

        chkdoccs2.setBackgroundColor(Color.GRAY);
        edtngaydoccu2.setBackgroundColor(Color.GRAY);
        edtngaydocmoi2.setBackgroundColor(Color.GRAY);
        edtSocu2.setBackgroundColor(Color.GRAY);
        edtSomoi2.setBackgroundColor(Color.GRAY);
        edttieuthu2.setBackgroundColor(Color.GRAY);
        btnLuuCS2.setBackgroundColor(Color.GRAY);

        tvNDC2.setBackgroundColor(Color.GRAY);
        tvNDM2.setBackgroundColor(Color.GRAY);
        tvSC2.setBackgroundColor(Color.GRAY);
        tvSM2.setBackgroundColor(Color.GRAY);



    }
    public void enableGhiChiSo1(){

        chkdoccs1.setEnabled(true);
        chkdoccs1.setChecked(true);
        edtngaydoccu1.setEnabled(true);
        edtngaydocmoi1.setEnabled(true);
        edtSocu1.setEnabled(true);
        edtSomoi1.setEnabled(true);
        edttieuthu1.setEnabled(true);
        btnLuuCS1.setEnabled(true);

        chkdoccs1.setBackgroundResource(R.drawable.bovien);
        edtngaydoccu1.setBackgroundResource(R.drawable.bovien);
        edtngaydocmoi1.setBackgroundResource(R.drawable.bovien);
        edtSocu1.setBackgroundResource(R.drawable.bovien);
        edtSomoi1.setBackgroundResource(R.drawable.bovien);
        edttieuthu1.setBackgroundResource(R.drawable.bovien);
        btnLuuCS1.setBackgroundResource(R.drawable.button_style);

        tvNDC1.setBackgroundResource(R.drawable.bovien);
        tvNDM1.setBackgroundResource(R.drawable.bovien);
        tvSC1.setBackgroundResource(R.drawable.bovien);
        tvSM1.setBackgroundResource(R.drawable.bovien);

        String str = format2.format(new Date()).toString();
        Log.d("ngaydocmoi" , str);
        edtngaydocmoi1.setText(format2.format(new Date()).toString());



    }
    public void enableGhiChiSo2(){

        chkdoccs2.setEnabled(true);
        chkdoccs2.setChecked(true);
        edtngaydoccu2.setEnabled(true);
        edtngaydocmoi2.setEnabled(true);
        edtSocu2.setEnabled(true);
        edtSomoi2.setEnabled(true);
        edttieuthu2.setEnabled(true);
        btnLuuCS2.setEnabled(true);

        chkdoccs2.setBackgroundResource(R.drawable.bovien);
        edtngaydoccu2.setBackgroundResource(R.drawable.bovien);
        edtngaydocmoi2.setBackgroundResource(R.drawable.bovien);
        edtSocu2.setBackgroundResource(R.drawable.bovien);
        edtSomoi2.setBackgroundResource(R.drawable.bovien);
        edttieuthu2.setBackgroundResource(R.drawable.bovien);
        btnLuuCS2.setBackgroundResource(R.drawable.button_style);

        tvNDC2.setBackgroundResource(R.drawable.bovien);
        tvNDM2.setBackgroundResource(R.drawable.bovien);
        tvSC2.setBackgroundResource(R.drawable.bovien);
        tvSM2.setBackgroundResource(R.drawable.bovien);


        String str = format2.format(new Date()).toString();
        Log.d("ngaydocmoi" , str);
        edtngaydocmoi2.setText(format2.format(new Date()).toString());

    }



    /*public boolean canhbaochenhlech(Integer luongtieuthu) {
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
    }*/


    private class HttpAsyncTaskGetCustomerInfo extends AsyncTask<String, JSONObject, Void> {
        Integer ms_dhk = null;
        Integer ms_dh  = null;
        Integer ms_tdhk  = null;
        Integer ms_tk  =null;
        String ten_dhk  =null;
        Integer so_thu_tu  =null;
        Integer so_tthu_cu1  =null;
        Integer chi_so_cu1  =null;
        Date ngay_doc_cu1  =null;
        Date ngay_doc_moi1 =null;
        Integer chi_so_moi1  =null;
        Integer s_tieu_thu1  =null;
        Integer  so_tthu_cu2  =null;
        Integer chi_so_cu2  =null;
        Date ngay_doc_cu2 =null;
        Integer chi_so_moi2  =null;
        Date ngay_doc_moi2  =null;
        Integer  s_tieu_thu2  =null;
        Integer ms_nhom  = null;
        Integer ms_tt_dh  = null;
        Integer co_chi_so  = null;
        Integer ms_bd  =null;
        Integer ms_phuong  =null;
        String ghi_chu  =null;
        String url_image =null;
        String so_seri  =null;
        Integer kha_nang_dh  =null;
        Integer he_so  =null;
        String mo_ta  =null;
        String ten_phuong  =null;


        JSONObject jsonKH;
        JSONArray jsonArray = new JSONArray();
        ProgressDialog progressDialog = new ProgressDialog(NhapSoBangTay.this);

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
                if (jsonArray.length() > 0) {
                    jsonKH = jsonArray.getJSONObject(0);
                    ms_dhk = Integer.parseInt(common.cat2kitucuoi(jsonKH.getString("ms_dhk")));
                    Ms_DH = Integer.parseInt(common.cat2kitucuoi(jsonKH.getString("ms_dh")));
                     ten_dhk = jsonKH.getString("ten_dhk");

                    String str_so_tthu_cu1= jsonKH.getString("so_tthu_cu1");
                    if(!"null".equals(str_so_tthu_cu1)){
                        So_TThu_Cu1 = jsonKH.getInt("so_tthu_cu1");
                    }else {

                    }

                    String str_chi_so_cu1= jsonKH.getString("chi_so_cu1");
                    if(!"null".equals(str_chi_so_cu1)){
                        chi_so_cu1 =  jsonKH.getInt("chi_so_cu1");
                    }

                    String str_ngay_doc_cu1 = jsonKH.getString("ngay_doc_cu1");
                    if(!"null".equals(str_ngay_doc_cu1)){
                        String ngay_doc_cu1_str = common.cat10kitucuoi(jsonKH.getString("ngay_doc_cu1"));
                        ngay_doc_cu1 = format1.parse(ngay_doc_cu1_str);
                    }

                    String str_ngay_doc_moi1 = jsonKH.getString("ngay_doc_moi1");
                    if(!"null".equals(str_ngay_doc_moi1)){
                        String ngay_doc_moi1_str = common.cat10kitucuoi(jsonKH.getString("ngay_doc_moi1"));
                        ngay_doc_moi1 = format1.parse(ngay_doc_moi1_str);
                    }

                    String str_chi_so_moi1= jsonKH.getString("chi_so_moi1");
                    if(!"null".equals(str_chi_so_moi1)){
                        chi_so_moi1  = jsonKH.getInt("chi_so_moi1");
                    }

                    String str_s_tieu_thu1= jsonKH.getString("s_tieu_thu1");
                    if(!"null".equals(str_s_tieu_thu1)){
                        s_tieu_thu1 = jsonKH.getInt("s_tieu_thu1");
                    }

                    String str_so_tthu_cu2= jsonKH.getString("so_tthu_cu2");
                    if(!"null".equals(str_so_tthu_cu2)){
                        so_tthu_cu2 = jsonKH.getInt("so_tthu_cu2");
                    }

                    String str_chi_so_cu2= jsonKH.getString("chi_so_cu2");
                    if(!"null".equals(str_chi_so_cu2)){
                        chi_so_cu2 = jsonKH.getInt("chi_so_cu2");
                    }

                    String str_ngay_doc_cu2 = jsonKH.getString("ngay_doc_cu2");
                    if(!"null".equals(str_ngay_doc_cu2)){
                        String ngay_doc_cu2_str = common.cat10kitucuoi(jsonKH.getString("ngay_doc_cu2"));
                        ngay_doc_cu2 =  format1.parse(ngay_doc_cu2_str);
                    }

                    String str_ngay_doc_moi2 = jsonKH.getString("ngay_doc_moi2");
                    if(!"null".equals(str_ngay_doc_moi2)){
                        String ngay_doc_moi2_str = common.cat10kitucuoi(jsonKH.getString("ngay_doc_moi2"));
                        ngay_doc_moi2 = format1.parse(ngay_doc_moi2_str);
                    }

                    String str_chi_so_moi2= jsonKH.getString("chi_so_moi2");
                    if(!"null".equals(str_chi_so_moi2)){
                        chi_so_moi2 = jsonKH.getInt("chi_so_moi2");
                    }

                    String str_s_tieu_thu2= jsonKH.getString("s_tieu_thu2");
                    if(!"null".equals(str_s_tieu_thu2)){
                        s_tieu_thu2 = jsonKH.getInt("s_tieu_thu2");
                    }

                    ms_nhom = Integer.parseInt(common.cat2kitucuoi(jsonKH.getString("ms_nhom")));
                    ms_tt_dh = Integer.parseInt(common.cat2kitucuoi(jsonKH.getString("ms_tt_dh")));
                    String co_chi_so_str = jsonKH.getString("co_chi_so");

                    if("true".equals(co_chi_so_str)){
                        co_chi_so = 1;
                    }

                    String str_ms_phuong= jsonKH.getString("ms_phuong");
                    if(!"null".equals(str_ms_phuong)){
                        ms_phuong = Integer.parseInt(common.cat2kitucuoi(jsonKH.getString("ms_phuong")));
                    }

                    String str_kha_nang_dh= jsonKH.getString("kha_nang_dh");
                    if(!"null".equals(str_kha_nang_dh)){
                        Kha_Nang_DH = jsonKH.getInt("kha_nang_dh");
                    }

                    String str_he_so= jsonKH.getString("he_so");
                    if(!"null".equals(str_he_so)){
                        He_So = Integer.parseInt(jsonKH.getString("he_so"));
                    }


                    so_seri = jsonKH.getString("so_seri");
                    mo_ta = common.GetDataToValue(jsonKH.getString("mo_ta"), "");
                    ten_phuong = common.GetDataToValue(jsonKH.getString("ten_phuong"), "");


                    //ms_ttrang = jsonKH.getInt("ms_ttrang");
                    //url_image = jsonKH.getString("url_image");

                } else {

                    Intent intent = new Intent(NhapSoBangTay.this, TabActivity.class);
                    intent.putExtra("ms_nhom", ms_nhom);
                    intent.putExtra("ms_tk", ms_tk);
                    intent.putExtra("ms_bd", ms_bd);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (jsonArray.length() > 0) {
                    tvTenTuyen.setText(mo_ta);
                    edtdanhba.setText(String.valueOf(ms_dhk));
                    edttendh.setText(String.valueOf(ten_dhk));
                    edtdiachi.setText(String.valueOf(ten_phuong));
                    edtseridh.setText(so_seri);
                    edtdiachi.setText(ten_phuong);

                    if(ngay_doc_cu1 ==null){
                        edtngaydoccu1.setText("");
                    }else {
                        edtngaydoccu1.setText(format2.format(ngay_doc_cu1));
                    }

                    if(ngay_doc_moi1 !=null){
                        edtngaydocmoi1.setText(format2.format(ngay_doc_moi1));
                    }
                    if(chi_so_cu1==null){
                        edtSocu1.setText(String.valueOf(0));
                    }else {
                        edtSocu1.setText(String.valueOf(chi_so_cu1));
                    }
                    if(chi_so_moi1==null){
                        edtSomoi1.setText(String.valueOf(""));
                    }else {
                        edtSomoi1.setText(String.valueOf(chi_so_moi1));
                    }

                    if(s_tieu_thu1==null){
                        edttieuthu1.setText(String.valueOf(""));
                    }else {
                        edttieuthu1.setText(String.valueOf(s_tieu_thu1));
                    }


                    if(ngay_doc_cu2 ==null){
                        edtngaydoccu2.setText("");
                    }else {
                        edtngaydoccu2.setText(format2.format(ngay_doc_cu2));
                    }

                    if(ngay_doc_moi2 !=null){

                        edtngaydocmoi2.setText(format2.format(ngay_doc_moi2));
                    }
                    if(chi_so_cu2==null){
                        edtSocu2.setText(String.valueOf(0));
                    }else {
                        edtSocu2.setText(String.valueOf(chi_so_cu2));
                    }

                    if(chi_so_moi2==null){
                        edtSomoi2.setText(String.valueOf(""));
                    }else {
                        edtSomoi2.setText(String.valueOf(chi_so_moi2));
                    }

                    if(s_tieu_thu2==null){
                        edttieuthu2.setText(String.valueOf(""));
                    }else {
                        edttieuthu2.setText(String.valueOf(s_tieu_thu2));
                    }



                    edtghichu.setText(ghi_chu);

                    // edtmdsd.setText(mo_ta_dong);
                    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    //Date ngaydoccu = (Date) sdf.parse();
                    //imgPhoto.setImageBitmap(null);
                } else {
                    showErrorDialogue(ms_db);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }


}
