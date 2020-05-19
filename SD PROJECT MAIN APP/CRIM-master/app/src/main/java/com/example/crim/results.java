package com.example.crim;

public class results {

    private String mage;
    private String mbmark;
    private String mdate;
    private String mhairclr;
    private String mheight;
    private String mname;
    private String msex;
    private String murl;

    public results() {
    }

    private results(String mname, String mage, String mbmark, String mdate, String mhairclr, String mheight, String msex, String murl) {
        this.mname = mname;
        this.mage = mage;
        this.mbmark = mbmark;
        this.mdate = mdate;
        this.mhairclr = mhairclr;
        this.mheight = mheight;
        this.msex = msex;
        this.murl = murl;
    }


    public String getMage() {
        return mage;
    }

    public void setMage(String mage) {
        this.mage = mage;
    }

    public String getMbmark() {
        return mbmark;
    }

    public void setMbmark(String mbmark) {
        this.mbmark = mbmark;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMhairclr() {
        return mhairclr;
    }

    public void setMhairclr(String mhairclr) {
        this.mhairclr = mhairclr;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMsex() {
        return msex;
    }

    public void setMsex(String msex) {
        this.msex = msex;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }
}
