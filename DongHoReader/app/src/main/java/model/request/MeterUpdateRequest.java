package model.request;

public class MeterUpdateRequest {
    private int ms_dhk;
    private int ms_tk;
    private Integer chi_so_moi;
    private  Integer so_tieu_thu;
    private int loai_chi_so;
    private String ghi_chu;


    public MeterUpdateRequest(int ms_dhk, int ms_tk, Integer chi_so_moi, Integer so_tieu_thu, int loai_chi_so, String ghi_chu) {
        this.ms_dhk = ms_dhk;
        this.ms_tk = ms_tk;
        this.chi_so_moi = chi_so_moi;
        this.so_tieu_thu = so_tieu_thu;
        this.loai_chi_so = loai_chi_so;
        this.ghi_chu = ghi_chu;
    }

    public Integer getChi_so_moi() {
        return chi_so_moi;
    }

    public void setChi_so_moi(Integer chi_so_moi) {
        this.chi_so_moi = chi_so_moi;
    }

    public Integer getSo_tieu_thu() {
        return so_tieu_thu;
    }

    public void setSo_tieu_thu(Integer so_tieu_thu) {
        this.so_tieu_thu = so_tieu_thu;
    }

    public int getLoai_chi_so() {
        return loai_chi_so;
    }

    public void setLoai_chi_so(int loai_chi_so) {
        this.loai_chi_so = loai_chi_so;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public int getMs_dhk() {
        return ms_dhk;
    }

    public void setMs_dhk(int ms_dhk) {
        this.ms_dhk = ms_dhk;
    }

    public int getMs_tk() {
        return ms_tk;
    }

    public void setMs_tk(int ms_tk) {
        this.ms_tk = ms_tk;
    }



}
