package model;

public class DiemDungKhongDoc {

    private int ms_mnoi;
    private String ten_kh;
    private int ms_tk;
    private int ms_tuyen;
    private int ms_ttrang;
    private String ngay;
    private byte[] anh;


    public DiemDungKhongDoc(int ms_mnoi, String ten_kh, int ms_tk, int ms_tuyen, int ms_ttrang, String ngay, byte[] anh) {
        this.ms_mnoi = ms_mnoi;
        this.ten_kh = ten_kh;
        this.ms_tk = ms_tk;
        this.ms_tuyen = ms_tuyen;
        this.ms_ttrang = ms_ttrang;
        this.ngay = ngay;
        this.anh = anh;
    }

    public String getTen_kh() {
        return ten_kh;
    }

    public void setTen_kh(String ten_kh) {
        this.ten_kh = ten_kh;
    }

    public DiemDungKhongDoc() {
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

    public int getMs_tuyen() {
        return ms_tuyen;
    }

    public void setMs_tuyen(int ms_tuyen) {
        this.ms_tuyen = ms_tuyen;
    }

    public int getMs_ttrang() {
        return ms_ttrang;
    }

    public void setMs_ttrang(int ms_ttrang) {
        this.ms_ttrang = ms_ttrang;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
