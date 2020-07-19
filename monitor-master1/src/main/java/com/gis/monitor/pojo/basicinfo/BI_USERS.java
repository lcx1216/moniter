package com.gis.monitor.pojo.basicinfo;

/*
    用户信息表
 */
public class BI_USERS {
    private String USERNAME;
    private String PASSWORD;
    private String REALNAME;
    private String TEL;
    private String EMAIL;
    private String DEPARTMENT;
    private String DETAILS;
    private String TYPE;
    private String SUBJECT;
    private String PROVINCEID;
    private String IS_ONLINE;
    private int ID;
    private String SUB_POWER_ID;

    public BI_USERS() {
    }

    public BI_USERS(String USERNAME, String PASSWORD, String REALNAME, String TEL, String EMAIL, String DEPARTMENT, String DETAILS, String TYPE, String SUBJECT, String PROVINCEID, String IS_ONLINE, int ID, String SUB_POWER_ID) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.REALNAME = REALNAME;
        this.TEL = TEL;
        this.EMAIL = EMAIL;
        this.DEPARTMENT = DEPARTMENT;
        this.DETAILS = DETAILS;
        this.TYPE = TYPE;
        this.SUBJECT = SUBJECT;
        this.PROVINCEID = PROVINCEID;
        this.IS_ONLINE = IS_ONLINE;
        this.ID = ID;
        this.SUB_POWER_ID = SUB_POWER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getREALNAME() {
        return REALNAME;
    }

    public void setREALNAME(String REALNAME) {
        this.REALNAME = REALNAME;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(String DETAILS) {
        this.DETAILS = DETAILS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getPROVINCEID() {
        return PROVINCEID;
    }

    public void setPROVINCEID(String PROVINCEID) {
        this.PROVINCEID = PROVINCEID;
    }

    public String getIS_ONLINE() {
        return IS_ONLINE;
    }

    public void setIS_ONLINE(String IS_ONLINE) {
        this.IS_ONLINE = IS_ONLINE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSUB_POWER_ID() {
        return SUB_POWER_ID;
    }

    public void setSUB_POWER_ID(String SUB_POWER_ID) {
        this.SUB_POWER_ID = SUB_POWER_ID;
    }
}
