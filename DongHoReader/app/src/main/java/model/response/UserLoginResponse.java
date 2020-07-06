package model.response;

public class UserLoginResponse {
    private String ms_bd;
    private String ten_bd;
    private String ghi_chu;

    public UserLoginResponse(String ms_bd, String ten_bd, String ghi_chu) {
        this.ms_bd = ms_bd;
        this.ten_bd = ten_bd;
        this.ghi_chu = ghi_chu;
    }

    public String getMs_bd() {
        return ms_bd;
    }

    public void setMs_bd(String ms_bd) {
        this.ms_bd = ms_bd;
    }

    public String getTen_bd() {
        return ten_bd;
    }

    public void setTen_bd(String ten_bd) {
        this.ten_bd = ten_bd;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }
}
