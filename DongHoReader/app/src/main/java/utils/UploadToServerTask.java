package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

/**
 * Created by  on 14/05/2015.
 */
public class UploadToServerTask extends AsyncTask<Void, Void, String> {

    //URL để tải hình lên server
    private String URL = "";
    private Activity context=null;
    private ProgressDialog progressDialog=null;
    private String ba1;
    public UploadToServerTask(Activity context, String url)
    {
        this.context=context;
        this.URL = url;
        this.progressDialog=new ProgressDialog(this.context);
    }
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog.setMessage("..............!");
        this.progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
    //Coding gửi hình lên Server
//        String url =  params[0];
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("ms_bd", "108"));
        nameValuePairs.add(new BasicNameValuePair("oldPassword", "1234"));
        nameValuePairs.add(new BasicNameValuePair("newPassword", "1"));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut();

            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httpPut);
            String st = EntityUtils.toString(response.getEntity());
            Log.v("log_tag", "" + st);

        } catch (Exception e) {
            Log.v("log_tag", "Lỗi kết nối : " + e.toString());
        }
        return "Thành công";

    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.progressDialog.hide();
        this.progressDialog.dismiss();
    }
}