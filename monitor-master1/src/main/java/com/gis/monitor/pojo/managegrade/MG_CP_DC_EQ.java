package com.gis.monitor.pojo.managegrade;


public class MG_CP_DC_EQ {
    private String REPORTER;
    private String INSERT_TIME;
    private String PROVINCE;
    private String STATIONID;
    private String STATIONNAME;
    private String ITEMSID;
    private String POINTID;
    private String INSERTUMENTINFORMATION;
    private String EARTHQUAKE_SITUATION;
    private String EARTHQUAKE_NUMBER;
    private String SUM_GRADE;
    private String FULL_GRADE;
    private String ISLASTEST;

    public MG_CP_DC_EQ(String REPORTER, String INSERT_TIME, String PROVINCE, String STATIONID, String STATIONNAME, String ITEMSID, String POINTID, String INSERTUMENTINFORMATION, String EARTHQUAKE_SITUATION, String EARTHQUAKE_NUMBER, String SUM_GRADE, String FULL_GRADE, String ISLASTEST) {
        this.REPORTER = REPORTER;
        this.INSERT_TIME = INSERT_TIME;
        this.PROVINCE = PROVINCE;
        this.STATIONID = STATIONID;
        this.STATIONNAME = STATIONNAME;
        this.ITEMSID = ITEMSID;
        this.POINTID = POINTID;
        this.INSERTUMENTINFORMATION = INSERTUMENTINFORMATION;
        this.EARTHQUAKE_SITUATION = EARTHQUAKE_SITUATION;
        this.EARTHQUAKE_NUMBER = EARTHQUAKE_NUMBER;
        this.SUM_GRADE = SUM_GRADE;
        this.FULL_GRADE = FULL_GRADE;
        this.ISLASTEST = ISLASTEST;
    }

    public String getREPORTER() {
        return REPORTER;
    }

    public void setREPORTER(String REPORTER) {
        this.REPORTER = REPORTER;
    }

    public String getINSERT_TIME() {
        return INSERT_TIME;
    }

    public void setINSERT_TIME(String INSERT_TIME) {
        this.INSERT_TIME = INSERT_TIME;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
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

    public String getITEMSID() {
        return ITEMSID;
    }

    public void setITEMSID(String ITEMSID) {
        this.ITEMSID = ITEMSID;
    }

    public String getPOINTID() {
        return POINTID;
    }

    public void setPOINTID(String POINTID) {
        this.POINTID = POINTID;
    }

    public String getINSERTUMENTINFORMATION() {
        return INSERTUMENTINFORMATION;
    }

    public void setINSERTUMENTINFORMATION(String INSERTUMENTINFORMATION) {
        this.INSERTUMENTINFORMATION = INSERTUMENTINFORMATION;
    }

    public String getEARTHQUAKE_SITUATION() {
        return EARTHQUAKE_SITUATION;
    }

    public void setEARTHQUAKE_SITUATION(String EARTHQUAKE_SITUATION) {
        this.EARTHQUAKE_SITUATION = EARTHQUAKE_SITUATION;
    }

    public String getEARTHQUAKE_NUMBER() {
        return EARTHQUAKE_NUMBER;
    }

    public void setEARTHQUAKE_NUMBER(String EARTHQUAKE_NUMBER) {
        this.EARTHQUAKE_NUMBER = EARTHQUAKE_NUMBER;
    }

    public String getSUM_GRADE() {
        return SUM_GRADE;
    }

    public void setSUM_GRADE(String SUM_GRADE) {
        this.SUM_GRADE = SUM_GRADE;
    }

    public String getFULL_GRADE() {
        return FULL_GRADE;
    }

    public void setFULL_GRADE(String FULL_GRADE) {
        this.FULL_GRADE = FULL_GRADE;
    }

    public String getISLASTEST() {
        return ISLASTEST;
    }

    public void setISLASTEST(String ISLASTEST) {
        this.ISLASTEST = ISLASTEST;
    }

    @Override
    public String toString() {
        return "MG_CP_DC_EQ{" +
                "REPORTER='" + REPORTER + '\'' +
                ", INSERT_TIME='" + INSERT_TIME + '\'' +
                ", PROVINCE='" + PROVINCE + '\'' +
                ", STATIONID='" + STATIONID + '\'' +
                ", STATIONNAME='" + STATIONNAME + '\'' +
                ", ITEMSID='" + ITEMSID + '\'' +
                ", POINTID='" + POINTID + '\'' +
                ", INSERTUMENTINFORMATION='" + INSERTUMENTINFORMATION + '\'' +
                ", EARTHQUAKE_SITUATION='" + EARTHQUAKE_SITUATION + '\'' +
                ", EARTHQUAKE_NUMBER='" + EARTHQUAKE_NUMBER + '\'' +
                ", SUM_GRADE='" + SUM_GRADE + '\'' +
                ", FULL_GRADE='" + FULL_GRADE + '\'' +
                ", ISLASTEST='" + ISLASTEST + '\'' +
                '}';
    }
}
