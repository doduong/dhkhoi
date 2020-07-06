package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import api.BarcodeReaderApiManager;
import model.BarcodeResponse;
import model.DongHoKhoi;
import model.SoTieuThu;
import model.ThongTinTieuThu;
import model.TinhtrangDocDongHo;
import model.request.UpdatePhoneNumber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CommonFuntions;
import utils.CommonText;
import utils.ReadJson;
import utils.SharedPref;

public class ThongTinChiTiet extends Activity {

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

    EditText edtseridh;
    EditText edtghichu;
    String ms_tk = "";
    Button btntruoc;
    Button btnsau;
    String ms_tuyen = "";
    DongHoKhoi ttkh;
    SharedPref config;
    CommonText common = new CommonText();
    ImageView imgPhoto;
    Spinner spinkhongdocduoc;
    ArrayList<TinhtrangDocDongHo> listTinhTrang;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");



    //bien toan cuc
    String ms_nhom = "";
    String ms_bd;
    boolean chkcachdoc;
    Integer Ms_DH = null;
    Integer So_TThu_Cu1 = 0;
    Integer So_TThu_Cu2 = 0;
    Integer He_So = 0;
    Integer Kha_Nang_DH = 0;

    CommonFuntions commonFuntions ;
    String savesdttemp= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String z = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet);


        config = new SharedPref(this);
        commonFuntions = new CommonFuntions();
        Intent intent = getIntent();
        ms_nhom = config.getString("ms_nhom", "");
        ms_tk = config.getString("ms_tk", "");
        ms_bd = config.getString("ms_bd", "");
        ttkh = (DongHoKhoi) intent.getSerializableExtra("ttct");
        final ArrayList<DongHoKhoi> arrTTCT = (ArrayList<DongHoKhoi>) intent.getSerializableExtra("arrlist");
        khaibaobien();
        getthongtinkhachhang(ttkh.getMs_dhk().toString());

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
       /* btnsau.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < arrTTCT.size() - 1; i++) {
                    if (ttkh.getStt_lo_trinh().equals(arrTTCT.get(i).getStt_lo_trinh())) {
                        Log.d("arrTTCT", arrTTCT.get(i).getMs_moinoi());
                        ttkh = arrTTCT.get(i + 1);
                        break;
                    }


                }
                GetPurposeByMoinoi(ttkh.getMs_moinoi());
                GetTopNInvoice(ttkh.getMs_moinoi());
                getthongtinkhachhang(ttkh.getMs_moinoi());


            }


        });

        btntruoc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 1; i < arrTTCT.size(); i++) {
                    if (ttkh.getStt_lo_trinh() == arrTTCT.get(i).getStt_lo_trinh()) {
                        ttkh = arrTTCT.get(i - 1);
                        break;
                    }

                }
                GetPurposeByMoinoi(ttkh.getMs_moinoi());
                GetTopNInvoice(ttkh.getMs_moinoi());
                getthongtinkhachhang(ttkh.getMs_moinoi());

            }


        });*/

    }

    public void khaibaobien(){
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
        edtseridh = (EditText) findViewById(R.id.edtsodonghobt);
        edtghichu = (EditText) findViewById(R.id.edtghichu);

    }
    public void getthongtinkhachhang(String ms_dhk) {
        String ms_tk1 = ms_tk;
        if (isConnected()) {
            String url = common.URL_API + "/getdhkbyid?ms_dhk=" + ms_dhk;
            Log.d("getdhkbyid", url);
            new HttpAsyncTaskGetDHKInfo().execute(url);
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




    private class HttpAsyncTaskGetDHKInfo extends AsyncTask<String, JSONObject, Void> {

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

        ProgressDialog progressDialog = new ProgressDialog(ThongTinChiTiet.this);

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

                    ghi_chu  = jsonKH.getString("ghi_chu");
                    if("null".equals(ghi_chu)||ghi_chu==null){
                        ghi_chu = "";
                    }

                    so_seri = jsonKH.getString("so_seri");
                    mo_ta = common.GetDataToValue(jsonKH.getString("mo_ta"), "");
                    ten_phuong = common.GetDataToValue(jsonKH.getString("ten_phuong"), "");




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
            } else {
                showErrorDialogue(String.valueOf(ms_dhk));
            }

            super.onPostExecute(result);
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }
    private int getIndexByString(Spinner spinner, String string) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void showErrorDialogue(final String resultscan) {
        Toast.makeText(this, "Mã danh bạ không tồn tại!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ThongTinChiTiet.this, TabActivity.class);
        startActivity(intent);
    }

    public void GetAllMeterStatus() {
        if (isConnected()) {
            String url = common.URL_API + "/GetAllMeterStatus";
            new HttpAsyncTaskGetAllMeterStatus().execute(url);
        } else {
            Toast.makeText(this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }

    }

    private class HttpAsyncTaskGetAllMeterStatus extends AsyncTask<String, JSONObject, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArrayTinhTrang;

            try {
                listTinhTrang = new ArrayList<>();
                jsonArrayTinhTrang = ReadJson.readJSonArrayFromURL(url);
                //Log.d("doInBackground", String.valueOf(jsonArrayTinhTrang.length()));
                for (int i = 0; i < jsonArrayTinhTrang.length(); i++) {
                    TinhtrangDocDongHo tinhtrangDocDongHo;
                    JSONObject objTinhTrang = jsonArrayTinhTrang.getJSONObject(i);
                    int ms_ttrang = objTinhTrang.getInt("ms_ttrang");
                    String mo_ta = objTinhTrang.getString("mo_ta");

                    tinhtrangDocDongHo = new TinhtrangDocDongHo(ms_ttrang, mo_ta);
                    listTinhTrang.add(tinhtrangDocDongHo);
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

            ArrayAdapter<TinhtrangDocDongHo> dataAdapter = new ArrayAdapter<TinhtrangDocDongHo>(ThongTinChiTiet.this, android.R.layout.simple_spinner_item, listTinhTrang);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinkhongdocduoc.setAdapter(dataAdapter);
        }
    }

    public void disableGhiChiSo1(){



        edtngaydoccu1.setEnabled(false);
        edtngaydocmoi1.setEnabled(false);
        edtSocu1.setEnabled(false);
        edtSomoi1.setEnabled(false);
        edttieuthu1.setEnabled(false);

        edtngaydoccu1.setBackgroundColor(Color.GRAY);
        edtngaydocmoi1.setBackgroundColor(Color.GRAY);
        edtSocu1.setBackgroundColor(Color.GRAY);
        edtSomoi1.setBackgroundColor(Color.GRAY);
        edttieuthu1.setBackgroundColor(Color.GRAY);

        tvNDC1.setBackgroundColor(Color.GRAY);
        tvNDM1.setBackgroundColor(Color.GRAY);
        tvSC1.setBackgroundColor(Color.GRAY);
        tvSM1.setBackgroundColor(Color.GRAY);


    }

    public void disableGhiChiSo2(){



        edtngaydoccu1.setEnabled(false);
        edtngaydocmoi1.setEnabled(false);
        edtSocu1.setEnabled(false);
        edtSomoi1.setEnabled(false);
        edttieuthu1.setEnabled(false);


        edtngaydoccu2.setBackgroundColor(Color.GRAY);
        edtngaydocmoi2.setBackgroundColor(Color.GRAY);
        edtSocu2.setBackgroundColor(Color.GRAY);
        edtSomoi2.setBackgroundColor(Color.GRAY);
        edttieuthu2.setBackgroundColor(Color.GRAY);

        tvNDC2.setBackgroundColor(Color.GRAY);
        tvNDM2.setBackgroundColor(Color.GRAY);
        tvSC2.setBackgroundColor(Color.GRAY);
        tvSM2.setBackgroundColor(Color.GRAY);



    }
    public void enableGhiChiSo1(){



        edtngaydoccu1.setEnabled(true);
        edtngaydocmoi1.setEnabled(true);
        edtSocu1.setEnabled(true);
        edtSomoi1.setEnabled(true);
        edttieuthu1.setEnabled(true);


        edtngaydoccu1.setBackgroundResource(R.drawable.bovien);
        edtngaydocmoi1.setBackgroundResource(R.drawable.bovien);
        edtSocu1.setBackgroundResource(R.drawable.bovien);
        edtSomoi1.setBackgroundResource(R.drawable.bovien);
        edttieuthu1.setBackgroundResource(R.drawable.bovien);

        tvNDC1.setBackgroundResource(R.drawable.bovien);
        tvNDM1.setBackgroundResource(R.drawable.bovien);
        tvSC1.setBackgroundResource(R.drawable.bovien);
        tvSM1.setBackgroundResource(R.drawable.bovien);




    }
    public void enableGhiChiSo2(){


        edtngaydoccu2.setEnabled(true);
        edtngaydocmoi2.setEnabled(true);
        edtSocu2.setEnabled(true);
        edtSomoi2.setEnabled(true);
        edttieuthu2.setEnabled(true);


        edtngaydoccu2.setBackgroundResource(R.drawable.bovien);
        edtngaydocmoi2.setBackgroundResource(R.drawable.bovien);
        edtSocu2.setBackgroundResource(R.drawable.bovien);
        edtSomoi2.setBackgroundResource(R.drawable.bovien);
        edttieuthu2.setBackgroundResource(R.drawable.bovien);


        tvNDC2.setBackgroundResource(R.drawable.bovien);
        tvNDM2.setBackgroundResource(R.drawable.bovien);
        tvSC2.setBackgroundResource(R.drawable.bovien);
        tvSM2.setBackgroundResource(R.drawable.bovien);



    }


}
