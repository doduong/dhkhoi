package app.cntt.cnhp.hpwaterbarcodereader;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.DongHoKhoi;
import model.ThongTinTieuThu;
import utils.DBManager;
import utils.SharedPref;

public class CustomAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<DongHoKhoi> listKH;
    private LayoutInflater mInflater = null;
    private DongHoKhoi tempValue = null;
    Integer tuyen  = null;
    int stt = 0;


    public CustomAdapter(Activity a, ArrayList<DongHoKhoi> d, Integer tuyen) {
        this.mActivity = a;
        this.listKH = d;
        this.tuyen = tuyen;
        stt = 0;
        // mInflater = (LayoutInflater)mActivity.getSystemService(
        //Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (listKH.size() <= 0) {
            return 1;
        }
        return listKH.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        private TextView tvMaKH;
        private TextView tvTenKH;
        private TextView tvcsm1;
        private TextView tvcsm2;
        private TextView tvstt;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //int stt = 0;

        LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        View vi = view;
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            vi = mInflater.inflate(R.layout.reportlayout, viewGroup, false);
            vi.requestFocus();
            vi.setMinimumHeight(50);
            holder.tvstt = vi.findViewById(R.id.tvstt);
            holder.tvMaKH = vi.findViewById(R.id.tvmakh);
            holder.tvTenKH = vi.findViewById(R.id.tvtenkh);
            //holder.tvDiaChi = vi.findViewById(R.id.tvdiachi);
            //holder.tvcsc = vi.findViewById(R.id.tvcsc);
            //holder.tvcsm = vi.findViewById(R.id.tvcsm);
            holder.tvcsm1 = vi.findViewById(R.id.tvcsm1);
            holder.tvcsm2 = vi.findViewById(R.id.tvcsm2);
            vi.setTag(holder);
        }
        //else {
        holder = (ViewHolder) vi.getTag();
        //holder = new ViewHolder();
        try {
            if (listKH.size() <= 0) {
                holder.tvMaKH.setText("");
                holder.tvTenKH.setText("");

            } else {
                tempValue = null;
                tempValue = (DongHoKhoi) listKH.get(i);

                if(tempValue.getDocdh() ==1){
                    holder.tvstt.setBackgroundColor(Color.WHITE);
                    holder.tvMaKH.setBackgroundColor(Color.WHITE);
                    holder.tvTenKH.setBackgroundColor(Color.WHITE);
                    holder.tvcsm1.setBackgroundColor(Color.WHITE);
                    holder.tvcsm2.setBackgroundColor(Color.WHITE);
                }else if (tempValue.getDocdh()==2) {
                    holder.tvstt.setBackgroundResource(R.color.dhcon);
                    holder.tvMaKH.setBackgroundResource(R.color.dhcon);
                    holder.tvTenKH.setBackgroundResource(R.color.dhcon);
                    holder.tvcsm1.setBackgroundResource(R.color.dhcon);
                    holder.tvcsm2.setBackgroundResource(R.color.dhcon);
                }else if (tempValue.getDocdh()==3){
                    holder.tvstt.setBackgroundResource(R.color.dockep);
                    holder.tvMaKH.setBackgroundResource(R.color.dockep);
                    holder.tvTenKH.setBackgroundResource(R.color.dockep);
                    holder.tvcsm1.setBackgroundResource(R.color.dockep);
                    holder.tvcsm2.setBackgroundResource(R.color.dockep);
                }



                    //holder.tvstt.setText(String.valueOf(tempValue.getStt_lo_trinh()));
                    holder.tvstt.setText(String.valueOf(i+1));
                    holder.tvMaKH.setText(String.valueOf(tempValue.getMs_dhk()));
                    holder.tvTenKH.setText(tempValue.getTen_dhk());
                    if(tempValue.getChi_so_moi1() != null) {
                        holder.tvcsm1.setText(String.valueOf(tempValue.getChi_so_moi1()));
                    }else {
                        holder.tvcsm1.setText("--");
                    }
                    if(tempValue.getChi_so_moi2() != null) {
                        holder.tvcsm2.setText(String.valueOf(tempValue.getChi_so_moi2()));
                    }else {
                        holder.tvcsm2.setText("--");
                    }





            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return vi;
    }
}
