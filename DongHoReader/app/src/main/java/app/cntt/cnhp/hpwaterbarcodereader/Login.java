package app.cntt.cnhp.hpwaterbarcodereader;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import api.BarcodeReaderApiManager;
import model.BarcodeResponse;
import model.request.UpdateUserPwd;
import model.response.UserLoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.BaseActivity;
import utils.CommonText;
import utils.ReadJson;
import utils.Server;
import utils.SharedPref;
import utils.UploadToServerTask;

public class Login extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    EditText username;
    EditText password;
    EditText edttk;
    Button btnLogin;
    public String z;
    boolean isSuccess = false;
    String ms_tk = "";
    String ten_tk = "";
    Connection conn;
    ImageView imgView;
    // GPSTrack gps;
    ProgressDialog progress;
    private Activity ac;
    private Dialog dialog;
    RadioButton radioButton;
    RadioButton radio_tldg;
    RadioButton radio_cltd;
    TextView edttldg;
    TextView edtcltd;
    //Button btnthaydoi;
    Button btnluu;
    //Button btnthietlap;
    Button btnchangepass;
    RadioGroup radioGroup;
    int tyledanhgia = 1;
    int TLDG = 0;
    int CLTD = 0;
    private Activity activity;
    private static final String KEY_REMEMBER = "app_remember";
    private static final String RADIO_TEXT_CLTD = "Chênh lệch tuyệt đối";
    private static final String KEY_USERNAME = "app_username";
    private static final String KEY_PASS = "app_password";
    CheckBox rem_userpass;
    SharedPref config;
    CommonText common = new CommonText();

    //khai báo dialog change password
    EditText edtuserdialog;
    EditText edtpassword;
    EditText edtnewpassword;
    EditText edtrepeatpassword;
    TextView tvthongbao;

    ProgressDialog progressDialog = null;
    //String version app
    String versionapp = "";
    String versionserver = "";
    TextView tvversion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edttk = (EditText) findViewById(R.id.kydoc);
        imgView = (ImageView) findViewById(R.id.imgView);
       // btnthietlap = (Button) findViewById(R.id.btnthietlap);
        rem_userpass = (CheckBox) findViewById(R.id.checkBox);
        btnchangepass = (Button) findViewById(R.id.btnchangepass);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        tvversion = (TextView) findViewById(R.id.tvversion);

        config = new SharedPref(this);

        if (config.getBoolean(KEY_REMEMBER, false)) {
            rem_userpass.setChecked(true);
        } else {
            rem_userpass.setChecked(false);
        }

        username.setText(config.getString(KEY_USERNAME, ""));
        username.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);


        if (isConnected()) {

            String url = common.URL_API + "/getthoiky";
            new HttpAsyncTaskGetMaxPeriod().execute(url);
        } else {
            Toast toast = Toast.makeText(Login.this, "Không có kết nối internet", Toast.LENGTH_LONG);
            toast.show();
        }


        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    username.setHint("");
                else
                    username.setHint("Tên đăng nhập");
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Mật khẩu");
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 123);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = "";
                String pass = "";
                versionapp = tvversion.getText().toString();

                if (isConnected()) {


                        if (!"".equals(edttk.getText().toString())) {

                            user = username.getText().toString();
                            pass = password.getText().toString();
                            if (!"".equals(user)) {
                                if (!"".equals(pass)) {
                                    CheckLogin(user, pass);
                                } else {
                                    Toast.makeText(Login.this, "Không để trống mật khẩu", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "Không để trống tên đăng nhập", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            String url = common.URL_API + "/getthoiky";
                            new HttpAsyncTaskGetMaxPeriod().execute(url);
                        }
                } else {

                    Toast.makeText(Login.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();

                }

            }


        });

        /*btnthietlap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                showDialog();

            }
        });*/

        btnchangepass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                showDialogChangePassword();

            }
        });


    }

    private class HttpAsyncTaskGetMaxPeriod extends AsyncTask<String, JSONObject, Void> {

        ProgressDialog progressDialog = new ProgressDialog(Login.this);

        protected void onPreExecute() {
            String ten_tk = "";
            super.onPreExecute();
            progressDialog.setMessage("Vui lòng chờ trong giây lát...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONObject objMaxPeriod;
            JSONArray jsonArray;
            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                objMaxPeriod = jsonArray.getJSONObject(0);
                ms_tk = objMaxPeriod.getString("ms_tk");
                ten_tk = objMaxPeriod.getString("thoi_ky");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            edttk.setText("TK: " + ten_tk);
            progressDialog.hide();
            progressDialog.dismiss();
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

    public void showDialog() {

        dialog = new Dialog(Login.this);
        dialog.setTitle("THIẾT LẬP CẢNH BÁO");
        dialog.setContentView(R.layout.ty_le_canh_bao);

        khaibaobien();
        //getThongSo();
        dialog.show();
    }

    public void showDialogChangePassword() {
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.Theme_AppCompat_Light_Dialog);
        final AlertDialog.Builder dialogchangepass = new AlertDialog.Builder(ctw);

        dialogchangepass.setTitle("THAY ĐỔI MẬT KHẨU");
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.change_password, null);

        edtuserdialog = (EditText) layout.findViewById(R.id.edtuserdialog);
        edtpassword = (EditText) layout.findViewById(R.id.edtpassword);
        edtnewpassword = (EditText) layout.findViewById(R.id.edtnewpassword);
        edtrepeatpassword = (EditText) layout.findViewById(R.id.edtrepeatpassword);

        dialogchangepass.setView(layout);

        dialogchangepass.setPositiveButton("THAY ĐỔI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isConnected()) {

                    if (!"".equals(edtuserdialog.getText().toString()) && !"".equals(edtpassword.getText().toString()) && !"".equals(edtnewpassword.getText().toString()) && !"".equals(edtrepeatpassword.getText().toString())) {
                        Log.d("edtpassword", edtpassword.getText().toString());

                        if (edtnewpassword.getText().toString().equals(edtrepeatpassword.getText().toString())) {
                            if (!edtpassword.getText().toString().equals(edtnewpassword.getText().toString())) {
                                int userId = 0;
                                try {
                                    userId = Integer.parseInt(edtuserdialog.getText().toString());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                updateUserPassword(userId, edtpassword.getText().toString(), edtnewpassword.getText().toString());
                                //UpdatePassword( "108",  "1234", "1") ;

                                //Toast.makeText(Login.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Login.this, "Mật khẩu cũ và mật khẩu mới không được trùng nhau!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(Login.this, "Mật khẩu mới và Gõ lại mật khẩu phải trùng nhau!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(Login.this, "Mật khẩu không được để trống!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Không có kết nối internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogchangepass.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;
        dialogchangepass.show().getWindow().setLayout(displayWidth, (int) (displayHeight * 0.6));
    }


    private void updateUserPassword(int userId, String oldPwd, String newPwd){
        UpdateUserPwd request = new UpdateUserPwd(userId,oldPwd,newPwd);
        BarcodeReaderApiManager.getInstance().accountApi().updateUserPassword(""+userId,request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        if(null !=response.body()){
                            //Log.d("update", response.body().string().toString());
                            String result = response.body().string().toString();
                            if(result.equals("true")){
                                Toast.makeText(Login.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(Login.this, "Sai tên đăng nhập hoặc mật khẩu cũ!", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(Login.this, "Sai tên đăng nhập hoặc mật khẩu cũ!", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(Login.this, "Lỗi trong quá trình cập nhật được mật khẩu!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
    /*public void getThongSo() {

        SharedPref config = new SharedPref(getApplicationContext());
        int tyledanhgia = config.getInt("tyledanhgia", 0);
        int TLDG = config.getInt("TLDG", 0);
        int CLTD = config.getInt("CLTD", 0);
        if (tyledanhgia == 1) {
            radio_tldg.setChecked(true);
            edttldg.setEnabled(true);
            edtcltd.setEnabled(false);
            //edttldg.setBackgroundColor(Color.RED);
            //edtcltd.setBackgroundColor(Color.GRAY);

        } else {
            radio_cltd.setChecked(true);
            edttldg.setEnabled(false);
            edtcltd.setEnabled(true);
            //edtcltd.setBackgroundColor(Color.RED);
            //edttldg.setBackgroundColor(Color.GRAY);
        }
        edttldg.setText(String.valueOf(TLDG));
        edtcltd.setText(String.valueOf(CLTD));

    }*/

    public void khaibaobien() {
        radio_cltd = (RadioButton) dialog.findViewById(R.id.radio_cltd);
        radio_tldg = (RadioButton) dialog.findViewById(R.id.radio_tldg);
        edttldg = (TextView) dialog.findViewById(R.id.edttldg);
        edtcltd = (TextView) dialog.findViewById(R.id.edtcltd);
        //btnthaydoi = (Button) dialog.findViewById(R.id.btnthaydoi);
        btnluu = (Button) dialog.findViewById(R.id.btnluu);
        radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);

        edttldg.setEnabled(false);
        edtcltd.setEnabled(false);

        edttldg.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_NORMAL);
        edtcltd.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_NORMAL);


        btnluu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {

                    RadioButton selectedRadioButton = (RadioButton) dialog.findViewById(selectedId);
                    String selectedRadioButtonText = selectedRadioButton.getText().toString();
                    //Log.d("selectedRadioButtonText", String.valueOf(selectedRadioButtonText));

                    if(selectedRadioButtonText.equals(RADIO_TEXT_CLTD)){
                        tyledanhgia = 0;
                    }else {
                        tyledanhgia = 1;
                    }

                    SharedPref config = new SharedPref(getApplicationContext());
                    config.putInt("tyledanhgia", tyledanhgia);
                    config.putInt("TLDG", Integer.parseInt(edttldg.getText().toString()));
                    config.putInt("CLTD", Integer.parseInt(edtcltd.getText().toString()));
                    config.commit();
                    dialog.dismiss();
                    Toast.makeText(Login.this, "Thiết lập cảnh báo thành công!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Login.this, "Bạn phải chọn hình thức cảnh báo!", Toast.LENGTH_LONG).show();
                }

               // String radiovalue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

            }
        });


    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this, Login.class);
        startActivity(intent);
        /*if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                myOwnBackPress();
            }
        }, 500);*/
    }

    private void myOwnBackPress() {
        if (!isFinishing()) {
            //super.onBackPressed();
            Intent intent = new Intent(Login.this, Login.class);
            startActivity(intent);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_cltd:
                if (checked) {
                    edtcltd.setEnabled(true);
                    //edtcltd.setBackgroundColor(Color.RED);
                    edttldg.setEnabled(false);
                    tyledanhgia = 0;
                }
                break;
            case R.id.radio_tldg:
                if (checked) {
                    edttldg.setEnabled(true);
                    // edttldg.setBackgroundColor(Color.RED);
                    edtcltd.setEnabled(false);
                    tyledanhgia = 1;
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();

    }

    private void managePrefs() {
        if (rem_userpass.isChecked()) {
            config.putString(KEY_USERNAME, username.getText().toString().trim());
            config.putString(KEY_PASS, password.getText().toString().trim());
            config.putBoolean(KEY_REMEMBER, true);
            config.commit();
        } else {
            config.putBoolean(KEY_REMEMBER, false);
            config.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            config.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            config.commit();

        }
    }

    public void CheckLogin(String msbd, String mat_khau) {
        if (isConnected()) {
            //CheckLogin?msbd=108&mat_khau=123456
            String url = common.URL_API + "/checklogin?ms_bd=" + msbd + "&mat_khau=" + mat_khau;
            Log.d("CheckLogin", url);
            new HttpAsyncTaskCheckLogin().execute(url);
            //checkLogin(msbd,mat_khau);
        } else {
            Toast.makeText(this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }
    }

    private  void checkLogin(String msbd, String mat_khau){

        BarcodeReaderApiManager.getInstance().accountApi().checkLogin(msbd,mat_khau).enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if(response.isSuccessful()){
                    config.putString("ms_bd", common.cat2kitucuoi(response.body().getMs_bd()));
                    config.putString("ms_tk", common.cat2kitucuoi(ms_tk));
                    config.commit();
                    Intent intent = new Intent(Login.this, TuyenDoc.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "Người dùng: " + response.body().getTen_bd(), Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(Login.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {

                Log.i("LOGIN","checkLogin err:" + t.getMessage());
            }
        });

    }

    private class HttpAsyncTaskCheckLogin extends AsyncTask<String, JSONObject, Void> {
        String ms_bdJson = "";
        String ten_bdJson = "";
        JSONObject objBD = null;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArray;
            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                if(jsonArray.length()>0){
                    objBD= jsonArray.getJSONObject(0);
                    ms_bdJson = common.cat2kitucuoi(objBD.getString("ms_bd"));
                    ten_bdJson = objBD.getString("ten_bd");
                } else {

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
            super.onPostExecute(result);
            if (null != objBD) {
                config.putString("ms_bd", ms_bdJson);
                config.putString("ms_tk", common.cat2kitucuoi(ms_tk));
                config.commit();

                Intent intent = new Intent(Login.this, TuyenDoc.class);
                startActivity(intent);


            } else {
                Toast.makeText(Login.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_LONG).show();
            }


        }
    }


    /*
       gps = new GPSTrack(this);
        if(gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
             Log.d("Your Location: ", "Lat: "+ latitude+ " Long: "+ longitude  );
        } */
    public void UpdatePassword(String msbd, String oldPassword, String newPassword) {
        if (isConnected()) {
            //CheckLogin?msbd=108&mat_khau=123456
            String url = common.URL_API + "/UpdatePassword?ms_bd=" + msbd + "&oldPassword=" + oldPassword + "&newPassword=" + newPassword;
            Log.d("UpdatePassword", url);
            new HttpAsyncTaskUpdatePassword().execute(url);
        } else {
            Toast.makeText(this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }
    }

    private class HttpAsyncTaskUpdatePassword extends AsyncTask<String, JSONObject, Void> {
        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        JSONObject objBD = null;

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Đang cập nhập...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];

            try {
                objBD = ReadJson.readJsonFromUrl(url);
                if (null != objBD) {
                } else {

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
            super.onPostExecute(result);

            progressDialog.hide();
            progressDialog.dismiss();
            Toast.makeText(Login.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_LONG).show();


        }
    }


}