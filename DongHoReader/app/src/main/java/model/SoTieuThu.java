package model;

public class SoTieuThu {

    private int ms_mnoi;
    private int ms_tk;
    private int so_tthu;

    public SoTieuThu(int ms_mnoi, int ms_tk, int so_tthu) {
        this.ms_mnoi = ms_mnoi;
        this.ms_tk = ms_tk;
        this.so_tthu = so_tthu;
    }

    public SoTieuThu() {
    }

    public int getMs_mnoi() {
        return ms_mnoi;
    }

    public void setMs_mnoi(int ms_mnoi) {
        this.ms_mnoi = ms_mnoi;
    }

    public int getMs_tk() {
        return ms_tk;
    }

    public void setMs_tk(int ms_tk) {
        this.ms_tk = ms_tk;
    }

    public int getSo_tthu() {
        return so_tthu;
    }

    public void setSo_tthu(int so_tthu) {
        this.so_tthu = so_tthu;
    }
}
