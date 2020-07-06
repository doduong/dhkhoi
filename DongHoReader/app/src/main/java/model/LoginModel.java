package model;

import android.content.Context;
import android.os.Environment;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import utils.SharedPref;

/**
 * Created by PHONG on 6/2/2016.
 */
public class LoginModel {

    public static String username;
    public static String pass;
    public static String hoten;
    public static String thang;
    public static String nam;
    public static String macv;
    public static String _database= Environment.getExternalStorageDirectory().getAbsolutePath() + "/EOSTN/"+"GHICHISOTN.DB.DB3";
    public static String _appname="CN TÂY NINH - WMS";
    public static String _url="http://115.74.196.52:8889/api/EOSTN/";

    public static void save(Context context) {
        SharedPref config = new SharedPref(context);
        config.putString("appvecto_username", username);
        config.putString("appvecto_pass", pass);
        config.putString("appvecto_thang", thang);
        config.putString("appvecto_nam", nam);

        config.commit();
    }

    public static boolean isLogin(Context context) {
        boolean flag = false;
        if (!isNullOrEmpty(getUsername(context)) ) {
            flag = true;
        }
        return flag;
    }

    public static void logout(Context context) {
        SharedPref config = new SharedPref(context);
        config.putString("appvecto_username", "");
        config.putString("appvecto_pass", "");
        config.putString("appvecto_hoten", "");
        config.putString("appvecto_macv", "");
        config.commit();
    }

    public static String getUsername(Context context) {
        SharedPref config = new SharedPref(context);
        username = config.getString("appvecto_username","");
        return username;
    }

    public static String getPass(Context context) {
        SharedPref config = new SharedPref(context);
        pass = config.getString("appvecto_pass", "");
        return pass;
    }

    public static String getHoten(Context context) {
        SharedPref config = new SharedPref(context);
        hoten = config.getString("appvecto_hoten", "");
        return hoten;
    }
    public static String getMacv(Context context) {
        SharedPref config = new SharedPref(context);
        macv = config.getString("appvecto_macv", "");
        return macv;
    }

    private static boolean isNullOrEmpty(String str) {
        boolean flag = false;
        if (str == null || str.isEmpty()) {
            flag = true;
        }
        return flag;
    }
    public static String formatNumber(int number) {

        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replaceAll(",", ".");
            return resp;
        } catch (Exception e) {
            return "";
        }
    }
}
