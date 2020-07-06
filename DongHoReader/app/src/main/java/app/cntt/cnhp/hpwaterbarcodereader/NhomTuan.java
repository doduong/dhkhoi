/*package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import model.Nhom;
import utils.BaseActivity;
import utils.CommonText;
import utils.ReadJson;
import utils.SharedPref;

public class NhomTuan extends BaseActivity {
    ArrayList<Nhom> listNhom;
    AdapterTuyenDoc adapterTuyenDoc;
    CommonText common = new CommonText();
    ListView lvnhomtuan;
    String ms_bd;
    String ms_tk = "";
    SharedPref config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhom_tuan);
        config = new SharedPref(this);
        ms_bd = config.getString("ms_bd", "");
        ms_tk = config.getString("ms_tk", "");
        lvnhomtuan = (ListView) findViewById(R.id.lvnhomtuan);

        if (isConnected()) {
            String url = common.URL_API + "/getnhomtuan";
            new HttpAsyncTaskGetNhomTuan().execute(url);

            lvnhomtuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (isConnected()) {
                        Nhom objnhom = listNhom.get(i);
                        config.putString("ten_nhom_tuan", objnhom.getMo_ta_());
                        config.putString("ms_nhom_tuan", String.valueOf(objnhom.getMs_nhom()));
                        config.commit();

                        Intent intent = new Intent(NhomTuan.this, TabActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NhomTuan.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            Toast.makeText(NhomTuan.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }



    }

    private class HttpAsyncTaskGetNhomTuan extends AsyncTask<String, JSONObject, Void> {
        ProgressDialog progressDialog = new ProgressDialog(NhomTuan.this);

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Nạp nhóm tuần...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArrayTuyen;

            try {
                listNhom = new ArrayList<>();
                jsonArrayTuyen = ReadJson.readJSonArrayFromURL(url);
                Log.d("doInBackground", String.valueOf(jsonArrayTuyen.length()));
                for (int i = 0; i < jsonArrayTuyen.length(); i++) {
                    Nhom tuyenDocCS;
                    JSONObject objTuyen = jsonArrayTuyen.getJSONObject(i);
                    int ms_nhom = objTuyen.getInt("ms_nhom_tuan");
                    String mo_ta = objTuyen.getString("mo_ta");
                    tuyenDocCS = new Nhom(ms_nhom,mo_ta);
                    listNhom.add(tuyenDocCS);
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
            adapterTuyenDoc = new AdapterTuyenDoc(NhomTuan.this, listNhom);
            lvnhomtuan.setAdapter(adapterTuyenDoc);
            adapterTuyenDoc.notifyDataSetChanged();
            super.onPostExecute(result);
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        }, 500);
    }

    private void myOwnBackPress() {
        if (!isFinishing()) {
            //super.onBackPressed();
            Intent intent = new Intent(NhomTuan.this, Login.class);
            startActivity(intent);
        }
    }

}
*/