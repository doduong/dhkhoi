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

public class DongHoKhoiDaDocAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<DongHoKhoi> listKH;
    private LayoutInflater mInflater = null;
    private DongHoKhoi tempValue = null;
    Integer tuyen  = null;
    int stt = 0;


    public DongHoKhoiDaDocAdapter(Activity a, ArrayList<DongHoKhoi> d, Integer tuyen) {
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder {
        private TextView tvMaKH;
        private TextView tvTenKH;
        private TextView tvsm1;
        private TextView tvsm2;
        private TextView tvstt;

    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        View vi = view;
        DongHoKhoiDaDocAdapter.ViewHolder holder;
        if (view == null) {
            holder = new DongHoKhoiDaDocAdapter.ViewHolder();

            vi = mInflater.inflate(R.layout.dhk_dadocduoc_layout, viewGroup, false);
            vi.requestFocus();
            vi.setMinimumHeight(50);
            holder.tvstt = vi.findViewById(R.id.tvstt);
            holder.tvMaKH = vi.findViewById(R.id.tvmakh);
            holder.tvTenKH = vi.findViewById(R.id.tvtenkh);
            holder.tvsm1 = vi.findViewById(R.id.tvsm1);
            holder.tvsm2 = vi.findViewById(R.id.tvsm2);
            vi.setTag(holder);
        }
        //else {
        holder = (DongHoKhoiDaDocAdapter.ViewHolder) vi.getTag();
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
                    holder.tvsm1.setBackgroundColor(Color.WHITE);
                    holder.tvsm2.setBackgroundColor(Color.WHITE);
                }else if (tempValue.getDocdh()==2) {
                    holder.tvstt.setBackgroundResource(R.color.dhcon);
                    holder.tvMaKH.setBackgroundResource(R.color.dhcon);
                    holder.tvTenKH.setBackgroundResource(R.color.dhcon);
                    holder.tvsm1.setBackgroundResource(R.color.dhcon);
                    holder.tvsm2.setBackgroundResource(R.color.dhcon);
                }else if (tempValue.getDocdh()==3){
                    holder.tvstt.setBackgroundResource(R.color.dockep);
                    holder.tvMaKH.setBackgroundResource(R.color.dockep);
                    holder.tvTenKH.setBackgroundResource(R.color.dockep);
                    holder.tvsm1.setBackgroundResource(R.color.dockep);
                    holder.tvsm2.setBackgroundResource(R.color.dockep);
                }



                //holder.tvstt.setText(String.valueOf(tempValue.getStt_lo_trinh()));
                holder.tvstt.setText(String.valueOf(i+1));
                holder.tvMaKH.setText(String.valueOf(tempValue.getMs_dhk()));
                holder.tvTenKH.setText(tempValue.getTen_dhk());

                if(tempValue.getChi_so_moi1() != null) {
                    holder.tvsm1.setText(String.valueOf(tempValue.getChi_so_moi1()));
                }else {
                    holder.tvsm1.setText("--");
                }

                if(tempValue.getChi_so_moi2()!= null) {
                    holder.tvsm2.setText(String.valueOf(tempValue.getChi_so_moi2()));
                }else {
                    holder.tvsm2.setText("--");
                }








            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return vi;
    }
}
