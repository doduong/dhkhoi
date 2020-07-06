package model;

import java.io.Serializable;
import java.util.Date;

public class KhachHang implements Serializable{

    private int ms_kh;
    private int ms_duong;
    private int ms_tt_kh;
    private int ms_loai_kh;
    private String ten_kh;
    private String dia_chi_kh;
    private String dien_thoai;
    private Date ngay_nhap_kh;
    private String ms_thue;
    private String so_tai_khoan;
    private String mo_tai;

    private int chi_so_cu;
    private int chi_so_moi;

    public int getChi_so_cu() {
        return chi_so_cu;
    }

    public void setChi_so_cu(int chi_so_cu) {
        this.chi_so_cu = chi_so_cu;
    }

    public int getChi_so_moi() {
        return chi_so_moi;
    }

    public void setChi_so_moi(int chi_so_moi) {
        this.chi_so_moi = chi_so_moi;
    }

    public KhachHang(int ms_kh, int ms_duong, int ms_tt_kh, int ms_loai_kh, String ten_kh, String dia_chi_kh, String dien_thoai, Date ngay_nhap_kh, String ms_thue, String so_tai_khoan, String mo_tai) {
        this.ms_kh = ms_kh;
        this.ms_duong = ms_duong;
        this.ms_tt_kh = ms_tt_kh;
        this.ms_loai_kh = ms_loai_kh;
        this.ten_kh = ten_kh;
        this.dia_chi_kh = dia_chi_kh;
        this.dien_thoai = dien_thoai;
        this.ngay_nhap_kh = ngay_nhap_kh;
        this.ms_thue = ms_thue;
        this.so_tai_khoan = so_tai_khoan;
        this.mo_tai = mo_tai;
    }

    public KhachHang() {
    }

    public KhachHang(int ms_kh, String ten_kh, String dia_chi_kh) {
        this.ms_kh = ms_kh;
        this.ten_kh = ten_kh;
        this.dia_chi_kh = dia_chi_kh;
    }

    public int getMs_kh() {
        return ms_kh;
    }

    public void setMs_kh(int ms_kh) {
        this.ms_kh = ms_kh;
    }

    public int getMs_duong() {
        return ms_duong;
    }

    public void setMs_duong(int ms_duong) {
        this.ms_duong = ms_duong;
    }

    public int getMs_tt_kh() {
        return ms_tt_kh;
    }

    public void setMs_tt_kh(int ms_tt_kh) {
        this.ms_tt_kh = ms_tt_kh;
    }

    public int getMs_loai_kh() {
        return ms_loai_kh;
    }

    public void setMs_loai_kh(int ms_loai_kh) {
        this.ms_loai_kh = ms_loai_kh;
    }

    public String getTen_kh() {
        return ten_kh;
    }

    public void setTen_kh(String ten_kh) {
        this.ten_kh = ten_kh;
    }

    public String getDia_chi_kh() {
        return dia_chi_kh;
    }

    public void setDia_chi_kh(String dia_chi_kh) {
        this.dia_chi_kh = dia_chi_kh;
    }

    public String getDien_thoai() {
        return dien_thoai;
    }

    public void setDien_thoai(String dien_thoai) {
        this.dien_thoai = dien_thoai;
    }

    public Date getNgay_nhap_kh() {
        return ngay_nhap_kh;
    }

    public void setNgay_nhap_kh(Date ngay_nhap_kh) {
        this.ngay_nhap_kh = ngay_nhap_kh;
    }

    public String getMs_thue() {
        return ms_thue;
    }

    public void setMs_thue(String ms_thue) {
        this.ms_thue = ms_thue;
    }

    public String getSo_tai_khoan() {
        return so_tai_khoan;
    }

    public void setSo_tai_khoan(String so_tai_khoan) {
        this.so_tai_khoan = so_tai_khoan;
    }

    public String getMo_tai() {
        return mo_tai;
    }

    public void setMo_tai(String mo_tai) {
        this.mo_tai = mo_tai;
    }


}
