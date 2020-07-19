package com.gis.monitor.pojo.qzdata;

/*

 */
public class QZ_DICT_STATIONS {
    private String STATIONID;
    private String STATIONNAME;
    private Double LATITUDE;
    private Double LONGITUDE;

    public QZ_DICT_STATIONS() {
    }

    public QZ_DICT_STATIONS(String STATIONID, String STATIONNAME, Double LATITUDE, Double LONGITUDE) {
        this.STATIONID = STATIONID;
        this.STATIONNAME = STATIONNAME;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
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

    public Double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(Double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public Double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(Double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }
}
