package model.request;

public class UpdatePhoneNumber {
    Integer MsMoiNoi;
    String UserPhoneNumber;

    public UpdatePhoneNumber(Integer msMoiNoi, String userPhoneNumber) {
        MsMoiNoi = msMoiNoi;
        UserPhoneNumber = userPhoneNumber;
    }

    public Integer getMsMoiNoi() {
        return MsMoiNoi;
    }

    public void setMsMoiNoi(Integer msMoiNoi) {
        MsMoiNoi = msMoiNoi;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }
}
