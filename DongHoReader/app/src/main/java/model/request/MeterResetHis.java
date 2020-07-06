package model.request;

public class MeterResetHis {
    private Integer ms_dh;
    private Integer ms_mnoi;
    private Integer chi_so_dh;
    private Integer so_lan_dao_so;


    public MeterResetHis(Integer ms_dh, Integer ms_mnoi, Integer chi_so_dh, Integer so_lan_dao_so) {
        this.ms_dh = ms_dh;
        this.ms_mnoi = ms_mnoi;
        this.chi_so_dh = chi_so_dh;
        this.so_lan_dao_so = so_lan_dao_so;
    }

    public Integer getMs_dh() {
        return ms_dh;
    }

    public void setMs_dh(Integer ms_dh) {
        this.ms_dh = ms_dh;
    }

    public Integer getMs_mnoi() {
        return ms_mnoi;
    }

    public void setMs_mnoi(Integer ms_mnoi) {
        this.ms_mnoi = ms_mnoi;
    }

    public Integer getChi_so_dh() {
        return chi_so_dh;
    }

    public void setChi_so_dh(Integer chi_so_dh) {
        this.chi_so_dh = chi_so_dh;
    }

    public Integer getSo_lan_dao_so() {
        return so_lan_dao_so;
    }

    public void setSo_lan_dao_so(Integer so_lan_dao_so) {
        this.so_lan_dao_so = so_lan_dao_so;
    }
}
