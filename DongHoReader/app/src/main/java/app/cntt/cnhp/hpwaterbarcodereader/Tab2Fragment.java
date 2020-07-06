package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.DiemDungMatMang;
import model.DongHoKhoi;
import model.ThongTinTieuThu;
import utils.CommonText;
import utils.DBManager;
import utils.ReadJson;
import utils.Server;
import utils.SharedPref;

public class Tab2Fragment extends Fragment
{
    Connection conn;
    private DongHoKhoiDaDocAdapter adapter;
    ListView lstpro;
    String ms_nhom = "";
    String ms_tk="";
    String ms_bd = "";
    int soluongchuadoc = 0;
    int soluongdadoc = 0;
    TextView tvtiledadoc;
    TextView tvtilechuadoc;
    TextView tongtieuthu;
    int tongtieuthudadoc = 0;
    EditText edtSearchName;
    Button btnSearchName;
    Button btnDocSo;
    String keysearch  = "";
    Spinner spidanhsachloc;
    Spinner spidstimkiem;
    int positionloaitk = 0;
    SharedPref config;
    CommonText common = new CommonText();
    ArrayList<DongHoKhoi> listdddd = new ArrayList<>();
    int countUsePointNotRead = 0;
    int getCountUsePointWereRead = 0;

    private View mRootView;
    DBManager dbManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.tab2_fragment, container, false);
        initView();
        return mRootView;
    }

    private void initView(){

        config = new SharedPref(getActivity());

        ms_nhom = config.getString("ms_nhom", "");
        ms_tk = config.getString("ms_tk", "");
        ms_bd = config.getString("ms_bd", "");

        lstpro = (ListView) mRootView.findViewById(R.id.lstpro2);
        tvtilechuadoc = (TextView) mRootView.findViewById(R.id.tvtilechuadoc2);
        tvtiledadoc = (TextView) mRootView.findViewById(R.id.tvtiledadoc2);
        edtSearchName = (EditText) mRootView.findViewById(R.id.edtsearchname);
        btnSearchName = (Button) mRootView.findViewById(R.id.btnsearchname);
        btnDocSo = (Button) mRootView.findViewById(R.id.btndocso);
        spidstimkiem = (Spinner)mRootView.findViewById(R.id.spidstimkiem) ;
        dbManager = new DBManager(getActivity());
        ArrayList<String> listTT1 = new ArrayList<String>();

        listTT1.add("Danh bạ");
        listTT1.add("Tên ĐHK");
        listTT1.add("Seri ĐH");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listTT1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spidstimkiem.setAdapter(dataAdapter1);


        spidstimkiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionloaitk = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        common.getDHKLQOfNhom(Integer.parseInt(ms_bd), Integer.parseInt(ms_nhom));

        getthongtindiemdung();
        //getCountUsePointNotRead();
        //getCountUsePointWereRead();



        btnDocSo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // new IntentIntegrator(getActivity()).setCaptureActivity(ScannerActivity.class).initiateScan();
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setBeepEnabled(false);
                integrator.forSupportFragment(Tab2Fragment.this).setCaptureActivity(ScannerActivity.class).initiateScan();

            }


        });

        btnSearchName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                keysearch = edtSearchName.getText().toString();
                searchUsePointWereRead(keysearch, positionloaitk);
            }


        });

        lstpro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (isConnected()) {


                        DongHoKhoi tttt = listdddd.get(i);
                        Intent intent = new Intent(getActivity(), ThongTinChiTiet.class);
                            intent.putExtra("arrlist", listdddd);
                            intent.putExtra("ttct", tttt);
                            intent.putExtra("ms_nhom", ms_nhom);
                            intent.putExtra("ms_tk", ms_tk);
                            startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_LONG).show();
                    }
                }


        });
    }

    public void searchUsePointWereRead(String keysearch, int conditionfield){
        String field = "";
        String keywrord = "&keyWord=" + keysearch;
        if (conditionfield == 0) {
            field = "&conditionField=ms_dhk";
        } else if (conditionfield == 1) {
            field = "&conditionField=ten_dhk";
        } else if (conditionfield == 2) {
            field = "&conditionField=seri_dh";
        }
        if (!"".equals(keysearch)) {
            if(isConnected()) {

                String url = common.URL_API + "/searchdhk?ms_nhom=" + ms_nhom + "&ms_bd=" + ms_bd+ keywrord + field;
                //new HttpAsyncTaskGetUsePointNotRead().execute(url);
                //SearchUsePointWereRead?ms_tuyen=5115&ms_tk=247&keyWord=%22%22&conditionField=customer'
                //String url = common.URL_API + "/SearchUsePointNotRead?ms_tuyen=" + ms_tuyen + "&ms_tk=" + ms_tk + keywrord + field;
                Log.d("SearchUsePointNotRead", url);
                new HttpAsyncTaskSearchUsePointWereRead().execute(url);
            }else  {
                Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
            }
        } else {
            getthongtindiemdung();
        }

    }

    public void getthongtindiemdung(){

        if(isConnected()) {

            String url = common.URL_API + "/getdhknhom?ms_nhom=" + ms_nhom + "&ms_bd=" + ms_bd;
            new HttpAsyncTaskGetUsePointWereRead().execute(url);

        }else {
            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Scan được hủy bỏ", Toast.LENGTH_LONG).show();
            } else {
                Log.d("showResultDialogue:",result.getContents());
                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    String ms_db = "";
    public void showResultDialogue( final String resultscan) {

        String ms_dhk = common.strimBarcode(resultscan);
        if (isConnected()) {
            //String url = common.URL_API + "/GetCustomerInfo?ms_mnoi=" + ms_db + "&ms_tk=" + ms_tk+"&ms_tuyen="+ms_nhom;
            //new HttpAsyncTaskGetCustomerInfo().execute(url);
            DongHoKhoi ttdhk = null;
            for(int i = 0; i<listdddd.size(); i++){
                if(listdddd.get(i).getMs_dhk().equals(Integer.parseInt(ms_dhk))){
                    ttdhk = listdddd.get(i);
                    break;
                }
            }
            if(ttdhk !=null) {

                Intent intent = new Intent(getActivity(), ThongTinChiTiet.class);
                intent.putExtra("ms_nhom", ms_nhom);
                intent.putExtra("arrlist", listdddd);
                intent.putExtra("ttct", ttdhk);
                intent.putExtra("ms_tk", ms_tk);
                // intent.putExtra("tongsodiemdung",String.valueOf(arrTD.size()) );
                startActivity(intent);
            }else {
                Toast.makeText(getActivity(), "Không tồn tại điểm dùng: "+ms_dhk+ " trong danh sách đã đọc!", Toast.LENGTH_LONG).show();
            }


        } else {

            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTaskGetUsePointWereRead extends AsyncTask<String, JSONObject, Void> {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        JSONArray jsonArrayTuyen;
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];

            String ngay_doc_moi_str = "";
            String dien_thoai ="";
            listdddd = new ArrayList<>();
            try {
                jsonArrayTuyen = ReadJson.readJSonArrayFromURL(url);
                tongtieuthudadoc = 0;
                for (int i = 0; i < jsonArrayTuyen.length(); i++) {
                    DongHoKhoi tttt;
                    JSONObject objTTTT = jsonArrayTuyen.getJSONObject(i);
                    Integer ms_dhk1 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_dhk")));
                    Integer ms_dh = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_dh")));
                    Integer ms_tdhk = null;
                    Integer ms_tk = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_tk")));
                    String ten_dhk = objTTTT.getString("ten_dhk") ;
                    Integer so_thu_tu =  Integer.parseInt(objTTTT.getString("so_thu_tu"));


                    Integer so_tthu_cu1 = null;
                    String str_so_tthu_cu1= objTTTT.getString("so_tthu_cu1");
                    if(!"null".equals(str_so_tthu_cu1)){
                        so_tthu_cu1 = objTTTT.getInt("so_tthu_cu1");
                    }

                    Integer chi_so_cu1 = null;
                    String str_chi_so_cu1= objTTTT.getString("chi_so_cu1");
                    if(!"null".equals(str_chi_so_cu1)){
                        chi_so_cu1 =  objTTTT.getInt("chi_so_cu1");
                    }

                    Date ngay_doc_cu1 = null;
                    String str_ngay_doc_cu1 = objTTTT.getString("ngay_doc_cu1");
                    if(!"null".equals(str_ngay_doc_cu1)){
                        String ngay_doc_cu1_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_cu1"));
                        ngay_doc_cu1 = format1.parse(ngay_doc_cu1_str);
                    }

                    Date ngay_doc_moi1 = null;
                    String str_ngay_doc_moi1 = objTTTT.getString("ngay_doc_moi1");
                    if(!"null".equals(str_ngay_doc_moi1)){
                        String ngay_doc_moi1_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_moi1"));
                        ngay_doc_moi1 = format1.parse(ngay_doc_moi1_str);
                    }



                    Integer chi_so_moi1 = null;
                    String str_chi_so_moi1= objTTTT.getString("chi_so_moi1");
                    if(!"null".equals(str_chi_so_moi1)){
                        chi_so_moi1  = objTTTT.getInt("chi_so_moi1");
                    }

                    Integer s_tieu_thu1 = null;
                    String str_s_tieu_thu1= objTTTT.getString("s_tieu_thu1");
                    if(!"null".equals(str_s_tieu_thu1)){
                        s_tieu_thu1 = objTTTT.getInt("s_tieu_thu1");
                    }

                    Integer so_tthu_cu2 = null;
                    String str_so_tthu_cu2= objTTTT.getString("so_tthu_cu2");
                    if(!"null".equals(str_so_tthu_cu2)){
                        so_tthu_cu2 = objTTTT.getInt("so_tthu_cu2");
                    }

                    Integer chi_so_cu2 = null;
                    String str_chi_so_cu2= objTTTT.getString("chi_so_cu2");
                    if(!"null".equals(str_chi_so_cu2)){
                        chi_so_cu2 = objTTTT.getInt("chi_so_cu2");
                    }

                    Date ngay_doc_cu2 = null;
                    String str_ngay_doc_cu2 = objTTTT.getString("ngay_doc_cu2");
                    if(!"null".equals(str_ngay_doc_cu2)){
                        String ngay_doc_cu2_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_cu2"));
                        ngay_doc_cu2 =  format1.parse(ngay_doc_cu2_str);
                    }

                    Integer chi_so_moi2 = null;
                    String str_chi_so_moi2= objTTTT.getString("chi_so_moi2");
                    if(!"null".equals(str_chi_so_moi2)){
                        chi_so_moi2 = objTTTT.getInt("chi_so_moi2");
                    }

                    Date ngay_doc_moi2 = null;
                    String str_ngay_doc_moi2 = objTTTT.getString("ngay_doc_moi2");
                    if(!"null".equals(str_ngay_doc_moi2)){
                        String ngay_doc_moi2_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_moi2"));
                        ngay_doc_moi2 = format1.parse(ngay_doc_moi2_str);
                    }


                    Integer s_tieu_thu2 = null;
                    String str_s_tieu_thu2= objTTTT.getString("s_tieu_thu2");
                    if(!"null".equals(str_s_tieu_thu2)){
                        s_tieu_thu2 = objTTTT.getInt("s_tieu_thu2");
                    }


                    Integer ms_nhom_detail = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_nhom")));
                    Integer ms_tt_dh = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_tt_dh")));

                    /*String co_chi_so_str = common.GetDataToValue(objTTTT.getString("co_chi_so"), "");
                    int co_chi_so = (int) Double.parseDouble((co_chi_so_str == "") ? "0" : co_chi_so_str);*/
                    String co_chi_so_str = objTTTT.getString("co_chi_so");
                    int co_chi_so = 0;
                    if("true".equals(co_chi_so_str)){
                        co_chi_so = 1;
                    }

                    Integer ms_bd = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_bd")));

                    Integer ms_phuong = null;
                    String str_ms_phuong= objTTTT.getString("ms_phuong");
                    if(!"null".equals(str_ms_phuong)){
                        ms_phuong = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_phuong")));
                    }

                    String ghi_chu  = objTTTT.getString("ghi_chu");
                    String url_image =objTTTT.getString("url_image");
                    Integer docdh = 0;
                    if(ms_nhom_detail.equals(Integer.parseInt(ms_nhom))){
                        docdh = 1;
                        if(!(chi_so_moi2 ==null)&&!(s_tieu_thu2==null)&&!(ngay_doc_moi2==null)){
                            tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                            listdddd.add(tttt);
                        }

                    }

                    for(int j= 0; j<common.lstDhLienQuan.size(); j++) {
                       // if(ms_dhk1.equals(480)) {
                            if (ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq()) && (!ms_nhom_detail.equals(Integer.parseInt(ms_nhom)))) {
                                docdh = 2;
                                if (!(chi_so_moi1 == null) && !(s_tieu_thu1 == null) && !(ngay_doc_moi1 == null)) {
                                    tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                    listdddd.add(tttt);
                                }
                            }
                       // }
                        if(ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq())&& (docdh.equals(1))){
                            docdh = 3;
                                for (int k = 0; k < listdddd.size(); k++) {
                                    if (listdddd.get(k).getMs_dhk().equals(ms_dhk1)) {
                                        listdddd.remove(k);
                                    }
                                }
                            //if(ms_dhk1.equals(480)) {
                                if (!(chi_so_moi2 == null) && !(s_tieu_thu2 == null) && !(ngay_doc_moi2 == null) && !(chi_so_moi1 == null) && !(s_tieu_thu1 == null) && !(ngay_doc_moi1 == null)) {



                                    tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                    listdddd.add(tttt);
                                }
                           // }
                        }
                    }

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
            adapter = new DongHoKhoiDaDocAdapter(getActivity(), listdddd, Integer.parseInt(ms_nhom));
            lstpro.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            lstpro.invalidateViews();
            lstpro.refreshDrawableState();
            tvtiledadoc.setText("Đã đọc: "+ ( listdddd.size()));

            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    private class HttpAsyncTaskSearchUsePointWereRead extends AsyncTask<String, JSONObject, Void> {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            Log.d("GetUsePointWereRead", url);
            JSONArray jsonArrayTuyen;
            String ngay_doc_moi_str = "";
            String dien_thoai ="";
            listdddd = new ArrayList<>();
            try {
                jsonArrayTuyen = ReadJson.readJSonArrayFromURL(url);
                tongtieuthudadoc = 0;
                for (int i = 0; i < jsonArrayTuyen.length(); i++) {
                    DongHoKhoi tttt;
                    JSONObject objTTTT = jsonArrayTuyen.getJSONObject(i);
                    Integer ms_dhk1 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_dhk")));
                    Integer ms_dh = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_dh")));
                    Integer ms_tdhk = null;
                    Integer ms_tk = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_tk")));
                    String ten_dhk = objTTTT.getString("ten_dhk") ;
                    Integer so_thu_tu =  Integer.parseInt(objTTTT.getString("so_thu_tu"));


                    Integer so_tthu_cu1 = null;
                    String str_so_tthu_cu1= objTTTT.getString("so_tthu_cu1");
                    if(!"null".equals(str_so_tthu_cu1)){
                        so_tthu_cu1 = objTTTT.getInt("so_tthu_cu1");
                    }

                    Integer chi_so_cu1 = null;
                    String str_chi_so_cu1= objTTTT.getString("chi_so_cu1");
                    if(!"null".equals(str_chi_so_cu1)){
                        chi_so_cu1 =  objTTTT.getInt("chi_so_cu1");
                    }

                    Date ngay_doc_cu1 = null;
                    String str_ngay_doc_cu1 = objTTTT.getString("ngay_doc_cu1");
                    if(!"null".equals(str_ngay_doc_cu1)){
                        String ngay_doc_cu1_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_cu1"));
                        ngay_doc_cu1 = format1.parse(ngay_doc_cu1_str);
                    }

                    Date ngay_doc_moi1 = null;
                    String str_ngay_doc_moi1 = objTTTT.getString("ngay_doc_moi1");
                    if(!"null".equals(str_ngay_doc_moi1)){
                        String ngay_doc_moi1_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_moi1"));
                        ngay_doc_moi1 = format1.parse(ngay_doc_moi1_str);
                    }



                    Integer chi_so_moi1 = null;
                    String str_chi_so_moi1= objTTTT.getString("chi_so_moi1");
                    if(!"null".equals(str_chi_so_moi1)){
                        chi_so_moi1  = objTTTT.getInt("chi_so_moi1");
                    }

                    Integer s_tieu_thu1 = null;
                    String str_s_tieu_thu1= objTTTT.getString("chi_so_moi1");
                    if(!"null".equals(str_s_tieu_thu1)){
                        s_tieu_thu1 = objTTTT.getInt("s_tieu_thu1");
                    }

                    Integer so_tthu_cu2 = null;
                    String str_so_tthu_cu2= objTTTT.getString("so_tthu_cu2");
                    if(!"null".equals(str_so_tthu_cu2)){
                        so_tthu_cu2 = objTTTT.getInt("so_tthu_cu2");
                    }

                    Integer chi_so_cu2 = null;
                    String str_chi_so_cu2= objTTTT.getString("chi_so_cu2");
                    if(!"null".equals(str_chi_so_cu2)){
                        chi_so_cu2 = objTTTT.getInt("chi_so_cu2");
                    }

                    Date ngay_doc_cu2 = null;
                    String str_ngay_doc_cu2 = objTTTT.getString("ngay_doc_moi1");
                    if(!"null".equals(str_ngay_doc_cu2)){
                        String ngay_doc_cu2_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_cu2"));
                        ngay_doc_cu2 =  format1.parse(ngay_doc_cu2_str);
                    }

                    Integer chi_so_moi2 = null;
                    String str_chi_so_moi2= objTTTT.getString("chi_so_moi2");
                    if(!"null".equals(str_chi_so_moi2)){
                        chi_so_moi2 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("chi_so_moi2")));
                    }

                    Date ngay_doc_moi2 = null;
                    String str_ngay_doc_moi2 = objTTTT.getString("ngay_doc_moi2");
                    if(!"null".equals(str_ngay_doc_moi2)){
                        String ngay_doc_moi2_str = common.cat10kitucuoi(objTTTT.getString("ngay_doc_moi2"));
                        ngay_doc_moi2 = format1.parse(ngay_doc_moi2_str);
                    }


                    Integer s_tieu_thu2 = null;
                    String str_s_tieu_thu2= objTTTT.getString("s_tieu_thu2");
                    if(!"null".equals(str_s_tieu_thu2)){
                        s_tieu_thu2 = objTTTT.getInt("s_tieu_thu2");
                    }


                    Integer ms_nhom_detail = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_nhom")));
                    Integer ms_tt_dh = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_tt_dh")));

                    /*String co_chi_so_str = common.GetDataToValue(objTTTT.getString("co_chi_so"), "");
                    int co_chi_so = (int) Double.parseDouble((co_chi_so_str == "") ? "0" : co_chi_so_str);*/
                    String co_chi_so_str = objTTTT.getString("co_chi_so");
                    int co_chi_so = 0;
                    if("true".equals(co_chi_so_str)){
                        co_chi_so = 1;
                    }

                    Integer ms_bd = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_bd")));

                    Integer ms_phuong = null;
                    String str_ms_phuong= objTTTT.getString("ms_phuong");
                    if(!"null".equals(str_s_tieu_thu2)){
                        ms_phuong = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_phuong")));
                    }

                    String ghi_chu  = objTTTT.getString("ghi_chu");
                    String url_image =objTTTT.getString("url_image");
                    Integer docdh = 0;
                    if(ms_nhom_detail.equals(Integer.parseInt(ms_nhom))){
                        docdh = 1;
                        if(!(chi_so_moi2 ==null)&&!(s_tieu_thu2==null)&&!(ngay_doc_moi2==null)){
                            tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                            listdddd.add(tttt);
                        }

                    }

                    for(int j= 0; j<common.lstDhLienQuan.size(); j++) {
                        if(ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq())&&(!ms_nhom_detail.equals(Integer.parseInt(ms_nhom)))){
                            docdh = 2;
                            if(!(chi_so_moi1 ==null)&&!(s_tieu_thu1==null)&&!(ngay_doc_moi1==null)){
                                tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                listdddd.add(tttt);
                            }
                        }
                        if(ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq())&& (docdh.equals(1))){
                            docdh = 3;
                            for(int k =0; k<listdddd.size(); k++){
                                if(listdddd.get(k).getMs_dhk().equals(ms_dhk1)){
                                    listdddd.remove(k);
                                }
                            }
                            if((!(chi_so_moi2 ==null)&&!(s_tieu_thu2==null)&&!(ngay_doc_moi2==null))&&(!(chi_so_moi1 ==null)&&!(s_tieu_thu1==null)&&!(ngay_doc_moi1==null))){

                                tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                listdddd.add(tttt);
                            }
                        }
                    }

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
            adapter = new DongHoKhoiDaDocAdapter(getActivity(), listdddd, Integer.parseInt(ms_nhom));
            lstpro.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            lstpro.invalidateViews();
            lstpro.refreshDrawableState();
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    private void getCountUsePointNotRead(){

        if(isConnected()) {
            String url = common.URL_API + "/GetCountUsePointNotRead?ms_tuyen=" + ms_nhom + "&ms_tk=" + ms_tk;
            Log.d("GetCountUsePointNotRead", url);
            new HttpAsyncTaskGetCountUsePointNotRead().execute(url);
        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }
    }


    private class HttpAsyncTaskGetCountUsePointNotRead extends AsyncTask<String, JSONObject, Void> {

        String str = "0";

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];

            try {
                String str= ReadJson.readStringFromURL(url);
                if(null!=str|| !"".equals(str)){
                    countUsePointNotRead = Integer.parseInt(str);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("countUsePointNotRead", String.valueOf(countUsePointNotRead));
            tvtilechuadoc.setText("C.Đọc: "+ String.valueOf(countUsePointNotRead));
            super.onPostExecute(result);
        }
    }
    private void getCountUsePointWereRead(){

        if(isConnected()) {
            String url = common.URL_API + "/GetCountUsePointWereRead?ms_tuyen=" + ms_nhom + "&ms_tk=" + ms_tk;
            Log.d("GetCountUsePointWereRea", url);
            new HttpAsyncTaskGetCountUsePointWereRead().execute(url);
        }else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_LONG).show();
        }
    }


    private class HttpAsyncTaskGetCountUsePointWereRead extends AsyncTask<String, JSONObject, Void> {

        String str = "0";

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];

            try {
                String str= ReadJson.readStringFromURL(url);
                if(null!=str|| !"".equals(str)){
                    getCountUsePointWereRead = Integer.parseInt(str);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("getCountUsePointWereRea", String.valueOf(getCountUsePointWereRead));
            tvtiledadoc.setText("Đ.Đọc: "+ String.valueOf(getCountUsePointWereRead));
            super.onPostExecute(result);
        }
    }

    private class HttpAsyncTaskGetCustomerInfo extends AsyncTask<String, JSONObject, Void> {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                if (jsonArray.length()>0) {
                    jsonObject = (JSONObject)jsonArray.get(0);


                    String ms_dh = common.cat2kitucuoi(jsonObject.getString("ms_dh"));
                    Log.d("showResultDialogue", ms_db);
                    Intent intent = new Intent(getActivity(), NhapSoActivity.class);
                    intent.putExtra("ms_tuyen", ms_nhom);
                    intent.putExtra("ms_tk", ms_tk);
                    intent.putExtra("ms_bd", ms_bd);
                    intent.putExtra("ms_db", ms_db);
                    intent.putExtra("ms_dh", ms_dh);
                    // intent.putExtra("tongsodiemdung",String.valueOf(arrTD.size()) );
                    startActivity(intent);

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
            if (jsonArray.length()>0) {

            }else {
                Toast.makeText(getActivity(), "Mã danh bạ "+ ms_db+ " không tồn tại!", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(result);

        }
    }

}
