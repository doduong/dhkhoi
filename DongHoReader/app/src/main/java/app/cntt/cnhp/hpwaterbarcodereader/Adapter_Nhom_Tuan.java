package app.cntt.cnhp.hpwaterbarcodereader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Adapter_Nhom_Tuan extends FragmentStatePagerAdapter {
    private String listTab[] = {"CHƯA ĐỌC TUẦN", "ĐÃ ĐỌC TUẦN", "KHÔNG ĐỌC TUẦN"};
    private Tab1Fragment_Tuan tab1 ;
    private  Tab2Fragment_Tuan tab2;
    private Tab3Fragment_Tuan tab3;

    public Adapter_Nhom_Tuan(FragmentManager fm) {
        super(fm);
        tab1 = new Tab1Fragment_Tuan();
        tab2 = new Tab2Fragment_Tuan();
        tab3 = new Tab3Fragment_Tuan();
        //tab5 = new Tab5_MatMang();
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
