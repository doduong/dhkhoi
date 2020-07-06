package model.response;

import java.io.Serializable;

public class ThongTinMoiNoi implements Serializable {

    Integer ms_tk ;
    Integer ms_mnoi;
    String nguoi_thue;
    String dia_chi_mnoi;
    String ngay_doc_cu;
    String ngay_doc_moi;
    Integer chi_so_cu;
    Integer  chi_so_moi;
    Integer so_tieu_thu;
    String   mo_ta_tuyen;
    Integer  ms_tuyen;
    String mo_ta_dong;
    String ms_md_chinh;
    Integer ms_dh;
    String dien_thoai;
    String so_seri;
    Integer stt_lo_trinh;
    String url_image;
    String ghi_chu;
    Boolean co_chi_so;
    Integer ms_ttrang;
    Integer ms_kh;

    public ThongTinMoiNoi() {
    }

    public ThongTinMoiNoi(Integer ms_tk, Integer ms_mnoi, String nguoi_thue, String dia_chi_mnoi, String ngay_doc_cu, String ngay_doc_moi, Integer chi_so_cu, Integer chi_so_moi, Integer so_tieu_thu, String mo_ta_tuyen, Integer ms_tuyen, String mo_ta_dong, String ms_md_chinh, Integer ms_dh, String dien_thoai, String so_seri, Integer stt_lo_trinh, String url_image, String ghi_chu, Boolean co_chi_so, Integer ms_ttrang, Integer ms_kh) {
        this.ms_tk = ms_tk;
        this.ms_mnoi = ms_mnoi;
        this.nguoi_thue = nguoi_thue;
        this.dia_chi_mnoi = dia_chi_mnoi;
        this.ngay_doc_cu = ngay_doc_cu;
        this.ngay_doc_moi = ngay_doc_moi;
        this.chi_so_cu = chi_so_cu;
        this.chi_so_moi = chi_so_moi;
        this.so_tieu_thu = so_tieu_thu;
        this.mo_ta_tuyen = mo_ta_tuyen;
        this.ms_tuyen = ms_tuyen;
        this.mo_ta_dong = mo_ta_dong;
        this.ms_md_chinh = ms_md_chinh;
        this.ms_dh = ms_dh;
        this.dien_thoai = dien_thoai;
        this.so_seri = so_seri;
        this.stt_lo_trinh = stt_lo_trinh;
        this.url_image = url_image;
        this.ghi_chu = ghi_chu;
        this.co_chi_so = co_chi_so;
        this.ms_ttrang = ms_ttrang;
        this.ms_kh = ms_kh;
    }

    public Integer getMs_tk() {
        return ms_tk;
    }

    public void setMs_tk(Integer ms_tk) {
        this.ms_tk = ms_tk;
    }

    public Integer getMs_mnoi() {
        return ms_mnoi;
    }

    public void setMs_mnoi(Integer ms_mnoi) {
        this.ms_mnoi = ms_mnoi;
    }

    public String getNguoi_thue() {
        return nguoi_thue;
    }

    public void setNguoi_thue(String nguoi_thue) {
        this.nguoi_thue = nguoi_thue;
    }

    public String getDia_chi_mnoi() {
        return dia_chi_mnoi;
    }

    public void setDia_chi_mnoi(String dia_chi_mnoi) {
        this.dia_chi_mnoi = dia_chi_mnoi;
    }

    public String getNgay_doc_cu() {
        return ngay_doc_cu;
    }

    public void setNgay_doc_cu(String ngay_doc_cu) {
        this.ngay_doc_cu = ngay_doc_cu;
    }

    public String getNgay_doc_moi() {
        return ngay_doc_moi;
    }

    public void setNgay_doc_moi(String ngay_doc_moi) {
        this.ngay_doc_moi = ngay_doc_moi;
    }

    public Integer getChi_so_cu() {
        return chi_so_cu;
    }

    public void setChi_so_cu(Integer chi_so_cu) {
        this.chi_so_cu = chi_so_cu;
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

    public String getMo_ta_tuyen() {
        return mo_ta_tuyen;
    }

    public void setMo_ta_tuyen(String mo_ta_tuyen) {
        this.mo_ta_tuyen = mo_ta_tuyen;
    }

    public Integer getMs_tuyen() {
        return ms_tuyen;
    }

    public void setMs_tuyen(Integer ms_tuyen) {
        this.ms_tuyen = ms_tuyen;
    }

    public String getMo_ta_dong() {
        return mo_ta_dong;
    }

    public void setMo_ta_dong(String mo_ta_dong) {
        this.mo_ta_dong = mo_ta_dong;
    }

    public String getMs_md_chinh() {
        return ms_md_chinh;
    }

    public void setMs_md_chinh(String ms_md_chinh) {
        this.ms_md_chinh = ms_md_chinh;
    }

    public Integer getMs_dh() {
        return ms_dh;
    }

    public void setMs_dh(Integer ms_dh) {
        this.ms_dh = ms_dh;
    }

    public String getDien_thoai() {
        return dien_thoai;
    }

    public void setDien_thoai(String dien_thoai) {
        this.dien_thoai = dien_thoai;
    }

    public String getSo_seri() {
        return so_seri;
    }

    public void setSo_seri(String so_seri) {
        this.so_seri = so_seri;
    }

    public Integer getStt_lo_trinh() {
        return stt_lo_trinh;
    }

    public void setStt_lo_trinh(Integer stt_lo_trinh) {
        this.stt_lo_trinh = stt_lo_trinh;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public Boolean getCo_chi_so() {
        return co_chi_so;
    }

    public void setCo_chi_so(Boolean co_chi_so) {
        this.co_chi_so = co_chi_so;
    }

    public Integer getMs_ttrang() {
        return ms_ttrang;
    }

    public void setMs_ttrang(Integer ms_ttrang) {
        this.ms_ttrang = ms_ttrang;
    }

    public Integer getMs_kh() {
        return ms_kh;
    }

    public void setMs_kh(Integer ms_kh) {
        this.ms_kh = ms_kh;
    }
}
