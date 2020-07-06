package model;

import java.sql.Date;

public class ToaDoDiemDung {

    private int stt;
    private int ma_diem_dung;
    private String tenkh;
    private String vido;
    private String kinhdo;
    private String caodo;
    private String diachi;
    private int idbiendoc;
    private int idtuyen;
    private int chi_so_moi;

    public ToaDoDiemDung(int stt, int ma_diem_dung, String tenkh, String vido, String kinhdo, String caodo, String diachi, int idbiendoc, int idtuyen) {
        this.stt = stt;
        this.ma_diem_dung = ma_diem_dung;
        this.tenkh = tenkh;
        this.vido = vido;
        this.kinhdo = kinhdo;
        this.caodo = caodo;
        this.diachi = diachi;
        this.idbiendoc = idbiendoc;
        this.idtuyen = idtuyen;
    }

    public int getChi_so_moi() {
        return chi_so_moi;
    }

    public void setChi_so_moi(int chi_so_moi) {
        this.chi_so_moi = chi_so_moi;
    }

    public ToaDoDiemDung() {
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getMa_diem_dung() {
        return ma_diem_dung;
    }

    public void setMa_diem_dung(int ma_diem_dung) {
        this.ma_diem_dung = ma_diem_dung;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getVido() {
        return vido;
    }

    public void setVido(String vido) {
        this.vido = vido;
    }

    public String getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(String kinhdo) {
        this.kinhdo = kinhdo;
    }

    public String getCaodo() {
        return caodo;
    }

    public void setCaodo(String caodo) {
        this.caodo = caodo;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getIdbiendoc() {
        return idbiendoc;
    }

    public void setIdbiendoc(int idbiendoc) {
        this.idbiendoc = idbiendoc;
    }

    public int getIdtuyen() {
        return idtuyen;
    }

    public void setIdtuyen(int idtuyen) {
        this.idtuyen = idtuyen;
    }
}
