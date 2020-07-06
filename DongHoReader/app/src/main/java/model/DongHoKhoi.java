package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DongHoKhoi implements Serializable{

    private Integer ms_dhk;

    private Integer ms_dh ;

    private Integer ms_tdhk ;

    private Integer ms_tk ;

    private String ten_dhk ;

    private Integer so_thu_tu ;

    private Integer so_tthu_cu1 ;

    private Integer chi_so_cu1 ;

    private Date ngay_doc_cu1 ;

    private Date ngay_doc_moi1;

    private Integer chi_so_moi1 ;

    private Integer s_tieu_thu1 ;

    private Integer  so_tthu_cu2 ;

    private Integer chi_so_cu2 ;

    private Date ngay_doc_cu2;

    private Integer chi_so_moi2 ;

    private Date ngay_doc_moi2 ;

    private Integer  s_tieu_thu2 ;

    private BigDecimal toa_do_bac ;

    private BigDecimal toa_do_dong ;

    private Integer ms_nhom ;

    private Integer ms_tt_dh ;

    private Integer co_chi_so ;

    private Integer ms_bd ;

    private Integer ms_phuong ;

    private String ghi_chu ;

    private String url_image;

    private String so_seri ;

    private Integer kha_nang_dh ;

    private Integer he_so ;

    private String mo_ta ;

    private String ten_phuong ;

    private Integer docdh;


    public DongHoKhoi() {
    }

    public DongHoKhoi(Integer ms_dhk, Integer ms_dh, Integer ms_tdhk, Integer ms_tk, String ten_dhk, Integer so_thu_tu, Integer so_tthu_cu1, Integer chi_so_cu1, Date ngay_doc_cu1, Date ngay_doc_moi1, Integer chi_so_moi1, Integer s_tieu_thu1, Integer so_tthu_cu2, Integer chi_so_cu2, Date ngay_doc_cu2, Integer chi_so_moi2, Date ngay_doc_moi2, Integer s_tieu_thu2, Integer ms_nhom, Integer ms_tt_dh, Integer co_chi_so, Integer ms_bd, Integer ms_phuong, String ghi_chu, String url_image, int docdh) {
        this.ms_dhk = ms_dhk;
        this.ms_dh = ms_dh;
        this.ms_tdhk = ms_tdhk;
        this.ms_tk = ms_tk;
        this.ten_dhk = ten_dhk;
        this.so_thu_tu = so_thu_tu;
        this.so_tthu_cu1 = so_tthu_cu1;
        this.chi_so_cu1 = chi_so_cu1;
        this.ngay_doc_cu1 = ngay_doc_cu1;
        this.ngay_doc_moi1 = ngay_doc_moi1;
        this.chi_so_moi1 = chi_so_moi1;
        this.s_tieu_thu1 = s_tieu_thu1;
        this.so_tthu_cu2 = so_tthu_cu2;
        this.chi_so_cu2 = chi_so_cu2;
        this.ngay_doc_cu2 = ngay_doc_cu2;
        this.chi_so_moi2 = chi_so_moi2;
        this.ngay_doc_moi2 = ngay_doc_moi2;
        this.s_tieu_thu2 = s_tieu_thu2;
        this.ms_nhom = ms_nhom;
        this.ms_tt_dh = ms_tt_dh;
        this.co_chi_so = co_chi_so;
        this.ms_bd = ms_bd;
        this.ms_phuong = ms_phuong;
        this.ghi_chu = ghi_chu;
        this.url_image = url_image;
        this.docdh = docdh;
    }

    public Integer getDocdh() {
        return docdh;
    }

    public void setDocdh(Integer docdh) {
        this.docdh = docdh;
    }

    public String getSo_seri() {
        return so_seri;
    }

    public void setSo_seri(String so_seri) {
        this.so_seri = so_seri;
    }

    public Integer getKha_nang_dh() {
        return kha_nang_dh;
    }

    public void setKha_nang_dh(Integer kha_nang_dh) {
        this.kha_nang_dh = kha_nang_dh;
    }

    public Integer getHe_so() {
        return he_so;
    }

    public void setHe_so(Integer he_so) {
        this.he_so = he_so;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getTen_phuong() {
        return ten_phuong;
    }

    public void setTen_phuong(String ten_phuong) {
        this.ten_phuong = ten_phuong;
    }

    public Integer getMs_dhk() {
        return ms_dhk;
    }

    public void setMs_dhk(Integer ms_dhk) {
        this.ms_dhk = ms_dhk;
    }

    public Integer getMs_dh() {
        return ms_dh;
    }

    public void setMs_dh(Integer ms_dh) {
        this.ms_dh = ms_dh;
    }

    public Integer getMs_tdhk() {
        return ms_tdhk;
    }

    public void setMs_tdhk(Integer ms_tdhk) {
        this.ms_tdhk = ms_tdhk;
    }

    public Integer getMs_tk() {
        return ms_tk;
    }

    public void setMs_tk(Integer ms_tk) {
        this.ms_tk = ms_tk;
    }

    public String getTen_dhk() {
        return ten_dhk;
    }

    public void setTen_dhk(String ten_dhk) {
        this.ten_dhk = ten_dhk;
    }

    public Integer getSo_thu_tu() {
        return so_thu_tu;
    }

    public void setSo_thu_tu(Integer so_thu_tu) {
        this.so_thu_tu = so_thu_tu;
    }

    public Integer getSo_tthu_cu1() {
        return so_tthu_cu1;
    }

    public void setSo_tthu_cu1(Integer so_tthu_cu1) {
        this.so_tthu_cu1 = so_tthu_cu1;
    }

    public Integer getChi_so_cu1() {
        return chi_so_cu1;
    }

    public void setChi_so_cu1(Integer chi_so_cu1) {
        this.chi_so_cu1 = chi_so_cu1;
    }

    public Date getNgay_doc_cu1() {
        return ngay_doc_cu1;
    }

    public void setNgay_doc_cu1(Date ngay_doc_cu1) {
        this.ngay_doc_cu1 = ngay_doc_cu1;
    }

    public Date getNgay_doc_moi1() {
        return ngay_doc_moi1;
    }

    public void setNgay_doc_moi1(Date ngay_doc_moi1) {
        this.ngay_doc_moi1 = ngay_doc_moi1;
    }

    public Integer getChi_so_moi1() {
        return chi_so_moi1;
    }

    public void setChi_so_moi1(Integer chi_so_moi1) {
        this.chi_so_moi1 = chi_so_moi1;
    }

    public Integer getS_tieu_thu1() {
        return s_tieu_thu1;
    }

    public void setS_tieu_thu1(Integer s_tieu_thu1) {
        this.s_tieu_thu1 = s_tieu_thu1;
    }

    public Integer getSo_tthu_cu2() {
        return so_tthu_cu2;
    }

    public void setSo_tthu_cu2(Integer so_tthu_cu2) {
        this.so_tthu_cu2 = so_tthu_cu2;
    }

    public Integer getChi_so_cu2() {
        return chi_so_cu2;
    }

    public void setChi_so_cu2(Integer chi_so_cu2) {
        this.chi_so_cu2 = chi_so_cu2;
    }

    public Date getNgay_doc_cu2() {
        return ngay_doc_cu2;
    }

    public void setNgay_doc_cu2(Date ngay_doc_cu2) {
        this.ngay_doc_cu2 = ngay_doc_cu2;
    }

    public Integer getChi_so_moi2() {
        return chi_so_moi2;
    }

    public void setChi_so_moi2(Integer chi_so_moi2) {
        this.chi_so_moi2 = chi_so_moi2;
    }

    public Date getNgay_doc_moi2() {
        return ngay_doc_moi2;
    }

    public void setNgay_doc_moi2(Date ngay_doc_moi2) {
        this.ngay_doc_moi2 = ngay_doc_moi2;
    }

    public Integer getS_tieu_thu2() {
        return s_tieu_thu2;
    }

    public void setS_tieu_thu2(Integer s_tieu_thu2) {
        this.s_tieu_thu2 = s_tieu_thu2;
    }



    public Integer getMs_tt_dh() {
        return ms_tt_dh;
    }

    public void setMs_tt_dh(Integer ms_tt_dh) {
        this.ms_tt_dh = ms_tt_dh;
    }

    public int getCo_chi_so() {
        return co_chi_so;
    }

    public void setCo_chi_so(int co_chi_so) {
        this.co_chi_so = co_chi_so;
    }

    public Integer getMs_bd() {
        return ms_bd;
    }

    public void setMs_bd(Integer ms_bd) {
        this.ms_bd = ms_bd;
    }

    public Integer getMs_phuong() {
        return ms_phuong;
    }

    public void setMs_phuong(Integer ms_phuong) {
        this.ms_phuong = ms_phuong;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public Integer getMs_nhom() {
        return ms_nhom;
    }

    public void setMs_nhom(Integer ms_nhom) {
        this.ms_nhom = ms_nhom;
    }
}
