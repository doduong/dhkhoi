package model.request;

public class UpdateUserPwd {
    private int UserId;
    private String OldPwd;
    private String NewPwd;

    public UpdateUserPwd(int userId, String oldPassword, String newPassword) {
        UserId = userId;
        OldPwd = oldPassword;
        NewPwd = newPassword;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getOldPassword() {
        return OldPwd;
    }

    public void setOldPassword(String oldPassword) {
        OldPwd = oldPassword;
    }

    public String getNewPassword() {
        return NewPwd;
    }

    public void setNewPassword(String newPassword) {
        NewPwd = newPassword;
    }
}
