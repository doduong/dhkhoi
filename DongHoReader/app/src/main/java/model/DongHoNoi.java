package model;

import java.sql.Date;

public class DongHoNoi {

    private int ms_mnoi;
    private int ms_tuyen;
    private String tenkh;
    private String diachi;
    private int ms_tk;
    private int ms_ttrang;
    private int chi_so_cu;
    private int chi_so_moi;
    private String ngay_doc_cu;
    private String ngay_doc_moi;
    private int so_tthu;


    public DongHoNoi(int ms_mnoi, int ms_tuyen, String tenkh, String diachi, int ms_tk, int ms_ttrang, int chi_so_cu, int chi_so_moi, String ngay_doc_cu, String ngay_doc_moi, int so_tthu) {
        this.ms_mnoi = ms_mnoi;
        this.ms_tuyen = ms_tuyen;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.ms_tk = ms_tk;
        this.ms_ttrang = ms_ttrang;
        this.chi_so_cu = chi_so_cu;
        this.chi_so_moi = chi_so_moi;
        this.ngay_doc_cu = ngay_doc_cu;
        this.ngay_doc_moi = ngay_doc_moi;
        this.so_tthu = so_tthu;
    }

    public int getMs_tuyen() {
        return ms_tuyen;
    }

    public void setMs_tuyen(int ms_tuyen) {
        this.ms_tuyen = ms_tuyen;
    }

    public DongHoNoi() {
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

    public int getMs_mnoi() {
        return ms_mnoi;
    }

    public void setMs_mnoi(int ms_mnoi) {
        this.ms_mnoi = ms_mnoi;
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

    public int getSo_tthu() {
        return so_tthu;
    }

    public void setSo_tthu(int so_tthu) {
        this.so_tthu = so_tthu;
    }
}
