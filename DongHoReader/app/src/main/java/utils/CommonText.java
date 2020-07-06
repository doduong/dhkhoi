package utils;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import app.cntt.cnhp.hpwaterbarcodereader.AdapterTuyenDoc;
import app.cntt.cnhp.hpwaterbarcodereader.TuyenDoc;
import model.DongHoLienQuan;
import model.Nhom;

public class CommonText {

    //SERVER THAT
    public  String URL_API = "http://113.160.100.217:8081/api/dhkhoi";
    public  String URL_IMAGE = "http://113.160.100.217:8081";
    //SERVER TEST
    //public  String URL_API = "http://123.26.252.98:8086/api/dhkhoi";
    //public  String URL_IMAGE = "http://123.26.252.98:8086";
    public ArrayList<DongHoLienQuan> lstDhLienQuan = null;

    //http://123.26.252.98:8084

    public CommonText() {
    }

    public String cat2kitucuoi(String str){
        return str.substring(0,str.length()-2);
    }
    public String cat2kitudauvacuoi(String str){
        return str.substring(1,str.length()-1);
    }
    public String cat10kitucuoi(String str){
        return str.substring(0,10);
    }

    public Bitmap getBitmapFromURL(String src){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        src = URL_IMAGE+src;
        try {
            URL url = new URL(src);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String strimBarcode(String resultscan){

        if(resultscan.length()>4) {
            resultscan = resultscan.substring(4, resultscan.length());
        }
        return resultscan;
    }

    public String GetDataToValue(Object Data, String sDefault)
    {
        String kq;
        if (Data==null||Data.toString().equals("null"))
        {
            kq=sDefault;
        }
        else
        {
            if (Data.toString().equals("")||Data.toString().equals("{}"))
            {
                kq=sDefault;
            }
            else   {kq=Data.toString();}
        }
        return kq;
    }

    public void getDHKLQOfNhom(Integer ms_bd, Integer ms_nhom) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = URL_API + "/getdhlienquan?ms_bd="+ms_bd+"&ms_nhom="+ms_nhom ;
        JSONArray jsonArrDHKLQ;
        try {
            lstDhLienQuan = new ArrayList<>();
            jsonArrDHKLQ = ReadJson.readJSonArrayFromURL(url);
            for (int i = 0; i < jsonArrDHKLQ.length(); i++) {
                DongHoLienQuan dhlq;
                JSONObject objDhLQ = jsonArrDHKLQ.getJSONObject(i);
                Integer ms_dhk_lq = Integer.parseInt(cat2kitucuoi(objDhLQ.getString("ms_dhk_lq")));
                Integer ms_dhk = Integer.parseInt(cat2kitucuoi(objDhLQ.getString("ms_dhk")));
                dhlq = new DongHoLienQuan(ms_dhk_lq,ms_dhk);
                lstDhLienQuan.add(dhlq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }






}
