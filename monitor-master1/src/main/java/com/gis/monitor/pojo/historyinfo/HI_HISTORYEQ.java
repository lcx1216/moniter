package com.gis.monitor.pojo.historyinfo;

public class HI_HISTORYEQ {
    private String LATI;
    private String LONGI;
    private String MAG;
    private String EQDATE;


    public HI_HISTORYEQ() {
    }

    public HI_HISTORYEQ(String LATI, String LONGI, String MAG, String EQDATE) {
        this.LATI = LATI;
        this.LONGI = LONGI;
        this.MAG = MAG;
        this.EQDATE = EQDATE;
    }

    public String getLATI() { return LATI; }

    public void setLATI(String LATI) {
        this.LATI = LATI;
    }

    public String getLONGI() {
        return LONGI;
    }

    public void setLONGI(String LONGI) {
        this.LONGI = LONGI;
    }

    public String getMAG() {
        return MAG;
    }

    public void setMAG(String MAG) {
        this.MAG = MAG;
    }

    public String getEQDATE() {
        return EQDATE;
    }

    public void setEQDATE(String EQDATE) {
        this.EQDATE = EQDATE;
    }



}
