package model;

public class DongHoLienQuan {

    public Integer ms_dhk_lq;
    public Integer ms_dhk;

    public DongHoLienQuan() {
    }

    public DongHoLienQuan(Integer ms_dhk_lq, Integer ms_dhk) {
        this.ms_dhk_lq = ms_dhk_lq;
        this.ms_dhk = ms_dhk;
    }

    public Integer getMs_dhk_lq() {
        return ms_dhk_lq;
    }

    public void setMs_dhk_lq(Integer ms_dhk_lq) {
        this.ms_dhk_lq = ms_dhk_lq;
    }

    public Integer getMs_dhk() {
        return ms_dhk;
    }

    public void setMs_dhk(Integer ms_dhk) {
        this.ms_dhk = ms_dhk;
    }
}
