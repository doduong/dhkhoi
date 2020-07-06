package model;

import java.io.Serializable;
import java.util.Date;

public class ThongTinTieuThu implements Serializable {

    private String ms_moinoi;
    private String nguoi_thue;
    private String dia_chi;
    private String chi_so_cu;
    private String chi_so_moi;
    private String so_tthu;
    private Date ngay_doc_cu;
    private Date ngay_doc_moi;
    private String stt_lo_trinh;
    private String dien_thoai;
    private String ms_dh;
    private String so_seri;
    private String url_image;
    private String ms_ttrang;


    public ThongTinTieuThu() {
    }

    public ThongTinTieuThu(String ms_moinoi, String nguoi_thue, String dia_chi, String chi_so_cu, String chi_so_moi, String so_tthu, Date ngay_doc_cu, Date ngay_doc_moi, String stt_lo_trinh, String dien_thoai, String ms_dh, String so_seri, String url_image, String ms_ttrang) {
        this.ms_moinoi = ms_moinoi;
        this.nguoi_thue = nguoi_thue;
        this.dia_chi = dia_chi;
        this.chi_so_cu = chi_so_cu;
        this.chi_so_moi = chi_so_moi;
        this.so_tthu = so_tthu;
        this.ngay_doc_cu = ngay_doc_cu;
        this.ngay_doc_moi = ngay_doc_moi;
        this.stt_lo_trinh = stt_lo_trinh;
        this.dien_thoai = dien_thoai;
        this.ms_dh = ms_dh;
        this.so_seri = so_seri;
        this.url_image = url_image;
        this.ms_ttrang = ms_ttrang;
    }

    public String getMs_ttrang() {
        return ms_ttrang;
    }

    public void setMs_ttrang(String ms_ttrang) {
        this.ms_ttrang = ms_ttrang;
    }

    public String getMs_dh() {
        return ms_dh;
    }

    public void setMs_dh(String ms_dh) {
        this.ms_dh = ms_dh;
    }

    public String getSo_seri() {
        return so_seri;
    }

    public void setSo_seri(String so_seri) {
        this.so_seri = so_seri;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getDien_thoai() {
        return dien_thoai;
    }

    public void setDien_thoai(String dien_thoai) {
        this.dien_thoai = dien_thoai;
    }

    public String getMs_moinoi() {
        return ms_moinoi;
    }

    public void setMs_moinoi(String ms_moinoi) {
        this.ms_moinoi = ms_moinoi;
    }

    public String getNguoi_thue() {
        return nguoi_thue;
    }

    public void setNguoi_thue(String nguoi_thue) {
        this.nguoi_thue = nguoi_thue;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getChi_so_cu() {
        return chi_so_cu;
    }

    public void setChi_so_cu(String chi_so_cu) {
        this.chi_so_cu = chi_so_cu;
    }

    public String getChi_so_moi() {
        return chi_so_moi;
    }

    public void setChi_so_moi(String chi_so_moi) {
        this.chi_so_moi = chi_so_moi;
    }

    public String getSo_tthu() {
        return so_tthu;
    }

    public void setSo_tthu(String so_tthu) {
        this.so_tthu = so_tthu;
    }

    public Date getNgay_doc_cu() {
        return ngay_doc_cu;
    }

    public void setNgay_doc_cu(Date ngay_doc_cu) {
        this.ngay_doc_cu = ngay_doc_cu;
    }

    public Date getNgay_doc_moi() {
        return ngay_doc_moi;
    }

    public void setNgay_doc_moi(Date ngay_doc_moi) {
        this.ngay_doc_moi = ngay_doc_moi;
    }

    public String getStt_lo_trinh() {
        return stt_lo_trinh;
    }

    public void setStt_lo_trinh(String stt_lo_trinh) {
        this.stt_lo_trinh = stt_lo_trinh;
    }
}
