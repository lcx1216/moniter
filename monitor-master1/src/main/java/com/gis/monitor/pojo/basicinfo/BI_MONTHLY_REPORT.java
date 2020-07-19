package com.gis.monitor.pojo.basicinfo;

public class BI_MONTHLY_REPORT {
    private String RATINGTIME;
    private String STATIONID;
    private String STATIONNAME;
    private String POINTID;
    private String ITEMID;
    private String SCORE;
    private String TYPE;
    private String STATE;
    private String ID;


    public BI_MONTHLY_REPORT(String RATINGTIME, String STATIONID, String STATIONNAME, String POINTID, String ITEMID, String SCORE, String TYPE, String STATE, String ID) {
        this.RATINGTIME = RATINGTIME;
        this.STATIONID = STATIONID;
        this.STATIONNAME = STATIONNAME;
        this.POINTID = POINTID;
        this.ITEMID = ITEMID;
        this.SCORE = SCORE;
        this.TYPE = TYPE;
        this.STATE = STATE;
        this.ID = ID;
    }
    public String getRATINGTIME() {
        return RATINGTIME;
    }

    public void setRATINGTIME(String RATINGTIME) {
        this.RATINGTIME = RATINGTIME;
    }

    public String getSTATIONID() {
        return STATIONID;
    }

    public void setSTATIONID(String STATIONID) {
        this.STATIONID = STATIONID;
    }

    public String getSTATIONNAME() {
        return STATIONNAME;
    }

    public void setSTATIONNAME(String STATIONNAME) {
        this.STATIONNAME = STATIONNAME;
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public String getPOINTID() {
        return POINTID;
    }

    public void setPOINTID(String POINTID) {
        this.POINTID = POINTID;
    }

    public String getSCORE() {
        return SCORE;
    }

    public void setSCORE(String SCORE) {
        this.SCORE = SCORE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }




}
