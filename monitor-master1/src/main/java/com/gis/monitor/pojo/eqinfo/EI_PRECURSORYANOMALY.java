package com.gis.monitor.pojo.eqinfo;

public class EI_PRECURSORYANOMALY {

    private String EQID;
    private String STATIONNAME;
    private String ITEMS;


    public EI_PRECURSORYANOMALY(String EQID,String STATIONNAME,String ITEMS){
        this.EQID = EQID;
        this.STATIONNAME = STATIONNAME;
        this.ITEMS = ITEMS;
    }

    public String getEQID() {
        return EQID;
    }

    public void setEQID(String EQID) {
        this.EQID = EQID;
    }

    public String getSTATIONNAME() {
        return STATIONNAME;
    }

    public void setSTATIONNAME(String ITEMID) {
        this.STATIONNAME = STATIONNAME;
    }

    public String getITEMS() {
        return ITEMS;
    }

    public void setITEMS(String ITEMS) {
        this.ITEMS = ITEMS;
    }


}
