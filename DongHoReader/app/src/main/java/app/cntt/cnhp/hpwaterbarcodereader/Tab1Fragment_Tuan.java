package app.cntt.cnhp.hpwaterbarcodereader;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.DongHoKhoi;
import utils.CommonText;
import utils.DBManager;
import utils.ReadJson;
import utils.SharedPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment_Tuan extends Fragment {

    ListView lstpro;
    String ms_tuyen = "";
    String ms_nhom = "";
    String ms_tk = "";
    String ms_bd = "";
    TextView tvtiledadoc;
    TextView tvtilechuadoc;
    EditText edtSearchName;
    Button btnSearchName;
    Button btnDocSo;
    String keysearch = "";
    Spinner spidstimkiem;
    int positionloaitk = 0;
    private Context context;
    TextView tvSpinerItem;
    SharedPref config;
    CommonText common = new CommonText();
    private View mRootView;
    int countUsePointNotRead = 0;
    int getCountUsePointWereRead = 0;
    ArrayList<DongHoKhoi> listddcd = new ArrayList<>();
    DBManager dbManager ;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");


    public Tab1Fragment_Tuan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1_fragment__tuan, container, false);
    }

    private void initView() {
        config = new SharedPref(getActivity());

        ms_nhom = config.getString("ms_nhom", "");
        ms_tk = config.getString("ms_tk", "");
        ms_bd = config.getString("ms_bd", "");

        lstpro = (ListView) mRootView.findViewById(R.id.lstpro);
        tvtilechuadoc = (TextView) mRootView.findViewById(R.id.tvtilechuadoc);
        tvtiledadoc = (TextView) mRootView.findViewById(R.id.tvtiledadoc);
        edtSearchName = (EditText) mRootView.findViewById(R.id.edtsearchname);
        btnSearchName = (Button) mRootView.findViewById(R.id.btnsearchname);
        btnDocSo = (Button) mRootView.findViewById(R.id.btndocso);
        spidstimkiem = (Spinner) mRootView.findViewById(R.id.spidstimkiem);
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

            }
        });

        common.getDHKLQOfNhom(Integer.parseInt(ms_bd), Integer.parseInt(ms_nhom));
        getThongTinDiemDung();
        /*getCountUsePointNotRead();
        getCountUsePointWereRead();*/



        btnDocSo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setBeepEnabled(false);
                //integrator.forSupportFragment(Tab1Fragment_Tuan.this).setCaptureActivity(ScannerActivity.class).initiateScan();

            }


        });

        btnSearchName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                keysearch = edtSearchName.getText().toString();
                //searchUsePointNotRead(keysearch, positionloaitk);
            }


        });


        lstpro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isConnected()) {
                    DongHoKhoi tttt = listddcd.get(i);

                    Intent intent = new Intent(getActivity(), NhapSoBangTay.class);
                    intent.putExtra("arrlist", listddcd);
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

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void getThongTinDiemDung() {

        if (isConnected()) {

            String url = common.URL_API + "/getdhknhom?ms_nhom=" + ms_nhom + "&ms_bd=" + ms_bd;
            new HttpAsyncTaskGetUsePointNotRead().execute(url);

        } else {

            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }

    private class HttpAsyncTaskGetUsePointNotRead extends AsyncTask<String, JSONObject, Void> {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Nạp dữ liệu...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArrayTuyen;
            String dien_thoai = "";
            listddcd = new ArrayList<>();

            try {
                jsonArrayTuyen = ReadJson.readJSonArrayFromURL(url);
                for (int i = 0; i < jsonArrayTuyen.length(); i++) {

                    //ms_dhk,ms_dh,ms_tdhk,ms_tk,ten_dhk,so_thu_tu,so_tthu_cu1," +
                    //"chi_so_cu1,ngay_doc_cu1,ngay_doc_moi1 ,chi_so_moi1,s_tieu_thu1, so_tthu_cu2 ,chi_so_cu2,ngay_doc_cu2,chi_so_moi2,ngay_doc_moi2" +
                    //        " ,s_tieu_thu2,toa_do_bac ,toa_do_dong ,ms_nhom ,ms_tt_dh,co_chi_so true,ms_bd ,ms_phuong, ghi_chu, url_image


                    DongHoKhoi tttt;
                    JSONObject objTTTT = jsonArrayTuyen.getJSONObject(i);
                    Integer ms_dhk1 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_dhk")));
                    Integer ms_dh = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_dh")));
                    Integer ms_tdhk = null;
                    Integer ms_tk = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("ms_tk")));
                    String ten_dhk = objTTTT.getString("ten_dhk") ;
                    Integer so_thu_tu =  Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("so_thu_tu")));


                    Integer so_tthu_cu1 = null;
                    String str_so_tthu_cu1= objTTTT.getString("so_tthu_cu1");
                    if(!"null".equals(str_so_tthu_cu1)){
                        so_tthu_cu1 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("so_tthu_cu1")));
                    }

                    Integer chi_so_cu1 = null;
                    String str_chi_so_cu1= objTTTT.getString("chi_so_cu1");
                    if(!"null".equals(str_chi_so_cu1)){
                        chi_so_cu1 =  Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("chi_so_cu1")));
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
                        chi_so_moi1  = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("chi_so_moi1")));
                    }

                    Integer s_tieu_thu1 = null;
                    String str_s_tieu_thu1= objTTTT.getString("chi_so_moi1");
                    if(!"null".equals(str_s_tieu_thu1)){
                        s_tieu_thu1 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("s_tieu_thu1")));
                    }

                    Integer so_tthu_cu2 = null;
                    String str_so_tthu_cu2= objTTTT.getString("chi_so_moi1");
                    if(!"null".equals(str_so_tthu_cu2)){
                        so_tthu_cu2 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("so_tthu_cu2")));
                    }

                    Integer chi_so_cu2 = null;
                    String str_chi_so_cu2= objTTTT.getString("chi_so_moi1");
                    if(!"null".equals(str_chi_so_cu2)){
                        chi_so_cu2 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("chi_so_cu2")));
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
                        s_tieu_thu2 = Integer.parseInt(common.cat2kitucuoi(objTTTT.getString("s_tieu_thu2")));
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
                        if((chi_so_moi2 ==null)&&(s_tieu_thu2==null)&&(ngay_doc_moi2==null)){
                            tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                            listddcd.add(tttt);
                        }
                    }

                    for(int j= 0; j<common.lstDhLienQuan.size(); j++) {
                        if(ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq())&&(!docdh.equals(1))){
                            docdh = 2;
                            if((chi_so_moi1 ==null)&&(s_tieu_thu1==null)&&(ngay_doc_moi1==null)){
                                tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                listddcd.add(tttt);
                            }
                        }
                        if(ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq())&& (docdh.equals(1))){
                            docdh = 3;
                            if(((chi_so_moi2 ==null)&&(s_tieu_thu2==null)&&(ngay_doc_moi2==null))||((chi_so_moi1 ==null)&&(s_tieu_thu1==null)&&(ngay_doc_moi1==null))){

                                for(int k =0; k<listddcd.size(); k++){
                                    if(listddcd.get(k).getMs_dhk().equals(ms_dhk1)){
                                        listddcd.remove(k);
                                    }
                                }
                                tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                listddcd.add(tttt);
                            }
                        }
                    }
                    //tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                    //listddcd.add(tttt);
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
            super.onPostExecute(result);
            //listddcd = new CustomAdapter(getActivity(), listddcd, Integer.parseInt(ms_nhom));
           /// lstpro.setAdapter(adapter);
           // adapter.notifyDataSetChanged();
            lstpro.invalidateViews();
            lstpro.refreshDrawableState();
            progressDialog.hide();
            progressDialog.dismiss();
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
                Log.d("showResultDialogue:", result.getContents());
                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    String ms_db = "";


    public void showResultDialogue(final String resultscan) {


        String ms_dhk = common.strimBarcode(resultscan);
        ms_dhk = "200";
        if (isConnected()) {
            DongHoKhoi ttdhk = null;
            for(int i = 0; i<listddcd.size(); i++){
                if(listddcd.get(i).getMs_dhk().equals(Integer.parseInt(ms_dhk))){
                    ttdhk = listddcd.get(i);
                    break;
                }
            }
            if(ttdhk !=null) {
                Intent intent = new Intent(getActivity(), NhapSoBangTay.class);
                intent.putExtra("ms_nhom", ms_nhom);
                intent.putExtra("arrlist", listddcd);
                intent.putExtra("ttct", ttdhk);
                // intent.putExtra("tongsodiemdung",String.valueOf(arrTD.size()) );
                startActivity(intent);
            }else {
                Toast.makeText(getActivity(), "Không tồn tại điểm dùng: "+ms_dhk+ " trong danh sách chưa đọc!", Toast.LENGTH_LONG).show();
            }



        } else {

            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }


    }


}
