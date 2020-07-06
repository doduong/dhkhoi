package model.request;

public class MeterUpdateKhongDocDuocRequest {
    private int ms_dhk;
    private int ms_tk;
    private String ghi_chu;
    private String base_64_image;

    public MeterUpdateKhongDocDuocRequest(int ms_dhk, int ms_tk, String ghi_chu, String base_64_image) {
        this.ms_dhk = ms_dhk;
        this.ms_tk = ms_tk;
        this.ghi_chu = ghi_chu;
        this.base_64_image = base_64_image;
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

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public String getBase_64_image() {
        return base_64_image;
    }

    public void setBase_64_image(String base_64_image) {
        this.base_64_image = base_64_image;
    }
}
