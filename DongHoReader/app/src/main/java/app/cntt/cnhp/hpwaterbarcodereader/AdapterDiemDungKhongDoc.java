package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


import model.DongHoKhoi;
import model.ThongTinTieuThu;
import utils.CommonText;

public class AdapterDiemDungKhongDoc extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<DongHoKhoi> listddkd;
    private LayoutInflater mInflater = null;
    private DongHoKhoi tempValue = null;
    CommonText common = new CommonText();

    public AdapterDiemDungKhongDoc(Activity a, ArrayList<DongHoKhoi> d) {
        this.mActivity = a;
        this.listddkd = d;

    }

    @Override
    public int getCount() {
        if(listddkd.size()<=0){
            return 0;
        }
        return listddkd.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        public TextView tvstt;
        public TextView tvdanhba;
        public TextView tvtenkh;
        public TextView tvmstt;
        public ImageView imgAnh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater mInflater = (LayoutInflater)mActivity.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);
        View vi = view;
        AdapterDiemDungKhongDoc.ViewHolder holder;
        if(view == null){
            holder = new AdapterDiemDungKhongDoc.ViewHolder();

            vi = mInflater.inflate(R.layout.adapter_diem_dung_khong_doc, viewGroup, false);
            holder.tvstt = vi.findViewById(R.id.tvstt);
            holder.tvdanhba = vi.findViewById(R.id.tvdanhba);
            holder.tvtenkh = vi.findViewById(R.id.tvtenkh);
            holder.tvmstt = vi.findViewById(R.id.tvmstt);
            holder.imgAnh = vi.findViewById(R.id.imgAnh);
            vi.setTag(holder);
        }
        holder = (AdapterDiemDungKhongDoc.ViewHolder) vi.getTag();
        try {
            if(listddkd.size()<=0){
                holder.tvstt.setText("NoData");
                holder.tvdanhba.setText("No Data");
                holder.tvtenkh.setText("No Data");
                holder.tvstt.setText("NoData");

            }else  {
                tempValue = null;
                tempValue = (DongHoKhoi) listddkd.get(i);

                holder.tvstt.setBackgroundColor(Color.WHITE);
                holder.tvdanhba.setBackgroundColor(Color.WHITE);
                holder.tvtenkh.setBackgroundColor(Color.WHITE);
                holder.tvstt.setBackgroundColor(Color.WHITE);

                holder.tvstt.setText(String.valueOf(i+1));
                holder.tvdanhba.setText(String.valueOf(tempValue.getMs_dhk()));
                holder.tvtenkh.setText(String.valueOf(tempValue.getTen_dhk()));
                holder.tvmstt.setText(String.valueOf(tempValue.getGhi_chu()));
                String url_image = tempValue.getUrl_image();
               // byte[] hinhAnh = tempValue.getAnh();
               Bitmap bitmap = common.getBitmapFromURL(url_image);
                holder.imgAnh.setImageBitmap(bitmap);

            }
        }catch (Exception ex) {

            ex.printStackTrace();
        }

        return vi;
    }
}
