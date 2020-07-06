package model.response;

public class ThongTinDaoSo {

    private Integer so_lan_dao_so;
    private Integer ms_dh;
    private Integer ms_ls_daoso;

    public ThongTinDaoSo(Integer so_lan_dao_so, Integer ms_dh, Integer ms_ls_daoso) {
        this.so_lan_dao_so = so_lan_dao_so;
        this.ms_dh = ms_dh;
        this.ms_ls_daoso = ms_ls_daoso;
    }

    public Integer getSo_lan_dao_so() {
        return so_lan_dao_so;
    }

    public void setSo_lan_dao_so(Integer so_lan_dao_so) {
        this.so_lan_dao_so = so_lan_dao_so;
    }

    public Integer getMs_dh() {
        return ms_dh;
    }

    public void setMs_dh(Integer ms_dh) {
        this.ms_dh = ms_dh;
    }

    public Integer getMs_ls_daoso() {
        return ms_ls_daoso;
    }

    public void setMs_ls_daoso(Integer ms_ls_daoso) {
        this.ms_ls_daoso = ms_ls_daoso;
    }
}
