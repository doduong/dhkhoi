package model.request;

public class CustomerInfoChange {
    private String TenDangNhap ;
    private Integer ms_kh;
    private String so_dien_thoai;

    public CustomerInfoChange(String tenDangNhap, Integer ms_kh, String so_dien_thoai) {
        this.TenDangNhap = tenDangNhap;
        this.ms_kh = ms_kh;
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public Integer getMs_kh() {
        return ms_kh;
    }

    public void setMs_kh(Integer ms_kh) {
        this.ms_kh = ms_kh;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }
}
