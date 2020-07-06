package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.DiemDungKhongDoc;
import model.DongHoKhoi;
import model.DongHoNoi;
import model.ThongTinTieuThu;
import model.TinhtrangDocDongHo;
import utils.CommonText;
import utils.DBManager;
import utils.ReadJson;
import utils.SharedPref;

public class Tab4_KhongDocDuoc extends Fragment {

    private View mRootView;
    private static final String TAG = "Tab1Fragment";

    private AdapterDiemDungKhongDoc adapter;
    public ArrayList<DiemDungKhongDoc> customlistViewValueArray = new ArrayList<DiemDungKhongDoc>();
    ListView lstpro1;
    String ms_nhom = "";
    String ms_tk="";
    String ms_bd = "";
    CommonText common = new CommonText();
    SharedPref config;
    ArrayList<DongHoKhoi> listddcd = new ArrayList<>();
    ArrayList<TinhtrangDocDongHo> listTinhTrang;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.tab4__khong_doc_duoc, container, false);
        initView();
        return mRootView;
    }

    private void initView(){
        config = new SharedPref(getActivity());
        lstpro1 = (ListView) mRootView.findViewById(R.id.lstkhongdocduoc);
        ms_nhom = config.getString("ms_nhom", "");
        ms_bd = config.getString("ms_bd","" );
        ms_tk = config.getString("ms_tk", "");
        common.getDHKLQOfNhom(Integer.parseInt(ms_bd), Integer.parseInt(ms_nhom));
        getThongTinDiemDung();

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
            new HttpAsyncTaskGetUsePointCouldNotRead().execute(url);

        } else {

            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }



    private class HttpAsyncTaskGetUsePointCouldNotRead extends AsyncTask<String, JSONObject, Void> {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Nạp dữ liệu...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
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
                    if(!"null".equals(url_image)) {
                        if (ms_nhom_detail.equals(Integer.parseInt(ms_nhom))) {
                            docdh = 1;
                            if ((chi_so_moi2 == null) && (s_tieu_thu2 == null) && !(ngay_doc_moi2 == null)) {
                                tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                listddcd.add(tttt);
                            }
                        }

                        for (int j = 0; j < common.lstDhLienQuan.size(); j++) {
                            if (ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq()) && (!docdh.equals(1))) {
                                docdh = 2;
                                if ((chi_so_moi1 == null) && (s_tieu_thu1 == null) &&(ngay_doc_moi1 == null) && !(ngay_doc_moi2 == null)) {
                                    tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                    listddcd.add(tttt);
                                }
                            }
                            if (ms_dhk1.equals(common.lstDhLienQuan.get(j).getMs_dhk_lq()) && (docdh.equals(1))) {
                                docdh = 3;
                                for (int k = 0; k < listddcd.size(); k++) {
                                    if (listddcd.get(k).getMs_dhk().equals(ms_dhk1)) {
                                        listddcd.remove(k);
                                    }
                                }
                                if (((chi_so_moi2 == null) && (s_tieu_thu2 == null) && (!(ngay_doc_moi2 == null)&& !"null".equals(url_image))) || ((chi_so_moi1 == null) && (s_tieu_thu1 == null) && (!(ngay_doc_moi2 == null)&&!"null".equals(url_image)))) {
                                    tttt = new DongHoKhoi(ms_dhk1, ms_dh, ms_tdhk, ms_tk, ten_dhk, so_thu_tu, so_tthu_cu1, chi_so_cu1, ngay_doc_cu1, ngay_doc_moi1, chi_so_moi1, s_tieu_thu1, so_tthu_cu2, chi_so_cu2, ngay_doc_cu2, chi_so_moi2, ngay_doc_moi2, s_tieu_thu2, ms_nhom_detail, ms_tt_dh, co_chi_so, ms_bd, ms_phuong, ghi_chu, url_image, docdh);
                                    listddcd.add(tttt);
                                }
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
            adapter = new AdapterDiemDungKhongDoc(getActivity(), listddcd);
            lstpro1.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            lstpro1.invalidateViews();
            lstpro1.refreshDrawableState();
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }









}
