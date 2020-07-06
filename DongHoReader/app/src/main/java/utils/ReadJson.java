package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by admin on 10/19/2015.
 */
public class ReadJson {
    public static String readAll(Reader rd)  {
        StringBuilder sb = new StringBuilder();
        int cp;
        try {
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();


    }
    /**
     * Hàm trả về JSONObject
     * @param url - Truyền link URL có định dạng JSON
     * @return - Trả về JSONOBject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        //String durl = URLEncoder.encode(url, "UTF-8");
        URL lurl = new URL(url);
        URLConnection urlcon = lurl.openConnection();
        urlcon.connect();


        //InputStream is = lurl.openStream();
        InputStream is = new java.io.BufferedInputStream(urlcon.getInputStream());
        try {
            //đọc nội dung với Unicode:
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static JSONArray readJSonArrayFromURL(String url) throws IOException, JSONException{
        URL lurl = new URL(url);
        URLConnection urlcon = lurl.openConnection();
        urlcon.connect();

        InputStream is = new java.io.BufferedInputStream(urlcon.getInputStream());

        try {
            //đọc nội dung với Unicode:
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);

            return json;
        } finally {
            is.close();
        }

    }


    public static String readStringFromURL(String url) throws IOException{
        URL lurl = new URL(url);
        URLConnection urlcon = lurl.openConnection();
        urlcon.connect();

        InputStream is = new java.io.BufferedInputStream(urlcon.getInputStream());

        try {
            //đọc nội dung với Unicode:
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(is, Charset.forName("UTF-8")));
            String value = readAll(rd);

            return value;
        } finally {
            is.close();
        }

    }

}

