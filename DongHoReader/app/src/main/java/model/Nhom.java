package model;

public class Nhom {
    private int ms_nhom;
    private String mo_ta;



    public Nhom() {
    }

    public Nhom(int ms_nhom, String mo_ta_) {
        this.ms_nhom = ms_nhom;
        this.mo_ta = mo_ta_;
    }

    public int getMs_nhom() {
        return ms_nhom;
    }

    public void setMs_nhom(int ms_nhom) {
        this.ms_nhom = ms_nhom;
    }

    public String getMo_ta_() {
        return mo_ta;
    }

    public void setMo_ta_(String mo_ta_) {
        this.mo_ta = mo_ta_;
    }
}
