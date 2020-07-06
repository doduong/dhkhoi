package model;

import java.io.Serializable;

public class DiemDungMatMang implements Serializable {
    private int ms_mnoi;
    private String tenkh;
    private String diachi;
    private int ms_tk;
    private int ms_ttrang;
    private String chi_so_cu;
    private String chi_so_moi;
    private String ngay_doc_cu;
    private String ngay_doc_moi;
    private String so_tthu;
    private int ms_tuyen;
    private int co_chi_so_moi;

    private String tieu_thu_3t;
    private String tieu_thu_tb;
    private String seri_dh;
    private String mdsd;
    private int ma_nguyen_nhan;
    private byte[] anh;
    private String textNguyenNhan;
    private String dien_thoai;
    private Integer khongdocduoc;
    private String ghi_chu;

    public DiemDungMatMang(int ms_mnoi, String tenkh, String diachi, int ms_tk, int ms_ttrang, String chi_so_cu, String chi_so_moi, String ngay_doc_cu, String ngay_doc_moi, String so_tthu, int ms_tuyen, int co_chi_so_moi, String tieu_thu_3t, String tieu_thu_tb, String seri_dh, String mdsd, int ma_nguyen_nhan, byte[] anh, String textNguyenNhan, String dien_thoai, Integer khongdocduoc, String ghi_chu) {
        this.ms_mnoi = ms_mnoi;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.ms_tk = ms_tk;
        this.ms_ttrang = ms_ttrang;
        this.chi_so_cu = chi_so_cu;
        this.chi_so_moi = chi_so_moi;
        this.ngay_doc_cu = ngay_doc_cu;
        this.ngay_doc_moi = ngay_doc_moi;
        this.so_tthu = so_tthu;
        this.ms_tuyen = ms_tuyen;
        this.co_chi_so_moi = co_chi_so_moi;
        this.tieu_thu_3t = tieu_thu_3t;
        this.tieu_thu_tb = tieu_thu_tb;
        this.seri_dh = seri_dh;
        this.mdsd = mdsd;
        this.ma_nguyen_nhan = ma_nguyen_nhan;
        this.anh = anh;
        this.textNguyenNhan = textNguyenNhan;
        this.dien_thoai = dien_thoai;
        this.khongdocduoc = khongdocduoc;
        this.ghi_chu = ghi_chu;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public Integer getKhongdocduoc() {
        return khongdocduoc;
    }

    public void setKhongdocduoc(Integer khongdocduoc) {
        this.khongdocduoc = khongdocduoc;
    }

    public String getDien_thoai() {
        return dien_thoai;
    }

    public void setDien_thoai(String dien_thoai) {
        this.dien_thoai = dien_thoai;
    }

    public String getTextNguyenNhan() {
        return textNguyenNhan;
    }

    public void setTextNguyenNhan(String textNguyenNhan) {
        this.textNguyenNhan = textNguyenNhan;
    }

    public String getTieu_thu_3t() {
        return tieu_thu_3t;
    }

    public void setTieu_thu_3t(String tieu_thu_3t) {
        this.tieu_thu_3t = tieu_thu_3t;
    }

    public String getTieu_thu_tb() {
        return tieu_thu_tb;
    }

    public void setTieu_thu_tb(String tieu_thu_tb) {
        this.tieu_thu_tb = tieu_thu_tb;
    }

    public String getSeri_dh() {
        return seri_dh;
    }

    public void setSeri_dh(String seri_dh) {
        this.seri_dh = seri_dh;
    }

    public String getMdsd() {
        return mdsd;
    }

    public void setMdsd(String mdsd) {
        this.mdsd = mdsd;
    }

    public int getMa_nguyen_nhan() {
        return ma_nguyen_nhan;
    }

    public void setMa_nguyen_nhan(int ma_nguyen_nhan) {
        this.ma_nguyen_nhan = ma_nguyen_nhan;
    }

    public int getMs_tuyen() {
        return ms_tuyen;
    }

    public void setMs_tuyen(int ms_tuyen) {
        this.ms_tuyen = ms_tuyen;
    }

    public int getCo_chi_so_moi() {
        return co_chi_so_moi;
    }

    public void setCo_chi_so_moi(int co_chi_so_moi) {
        this.co_chi_so_moi = co_chi_so_moi;
    }

    public DiemDungMatMang() {
    }

    public int getMs_mnoi() {
        return ms_mnoi;
    }

    public void setMs_mnoi(int ms_mnoi) {
        this.ms_mnoi = ms_mnoi;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getMs_tk() {
        return ms_tk;
    }

    public void setMs_tk(int ms_tk) {
        this.ms_tk = ms_tk;
    }

    public int getMs_ttrang() {
        return ms_ttrang;
    }

    public void setMs_ttrang(int ms_ttrang) {
        this.ms_ttrang = ms_ttrang;
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

    public String getSo_tthu() {
        return so_tthu;
    }

    public void setSo_tthu(String so_tthu) {
        this.so_tthu = so_tthu;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
