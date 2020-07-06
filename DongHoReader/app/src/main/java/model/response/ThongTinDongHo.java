package model.response;

public class ThongTinDongHo {
    private Integer ms_dh;
    private Integer he_so;
    private Integer kha_nang_dh;

    public ThongTinDongHo(Integer ms_dh, Integer he_so, Integer kha_nang_dh) {
        this.ms_dh = ms_dh;
        this.he_so = he_so;
        this.kha_nang_dh = kha_nang_dh;
    }

    public Integer getMs_dh() {
        return ms_dh;
    }

    public void setMs_dh(Integer ms_dh) {
        this.ms_dh = ms_dh;
    }

    public Integer getHe_so() {
        return he_so;
    }

    public void setHe_so(Integer he_so) {
        this.he_so = he_so;
    }

    public Integer getKha_nang_dh() {
        return kha_nang_dh;
    }

    public void setKha_nang_dh(Integer kha_nang_dh) {
        this.kha_nang_dh = kha_nang_dh;
    }
}
