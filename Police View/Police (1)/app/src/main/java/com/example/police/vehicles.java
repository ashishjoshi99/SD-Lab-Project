package com.example.police;

public class vehicles {
    private String chas_no;
    private String eng_no;
    private String reg_no;
    private String veh_type;
    private String veh_img;

    public vehicles() {
    }

    private vehicles(String chas_no, String eng_no, String reg_no, String veh_type, String veh_img) {

        this.chas_no = chas_no;
        this.eng_no = eng_no;
        this.reg_no = reg_no;
        this.veh_type = veh_type;
        this.veh_img = veh_img;
    }

    public String getChas_no() {
        return chas_no;
    }

    public void setChas_no(String chas_no) {
        this.chas_no = chas_no;
    }

    public String getEng_no() {
        return eng_no;
    }

    public void setEng_no(String eng_no) {
        this.eng_no = eng_no;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getVeh_type() {
        return veh_type;
    }

    public void setVeh_type(String veh_type) {
        this.veh_type = veh_type;
    }

    public String getVeh_img() {
        return veh_img;
    }

    public void setVeh_img(String veh_img) {
        this.veh_img = veh_img;
    }
}
