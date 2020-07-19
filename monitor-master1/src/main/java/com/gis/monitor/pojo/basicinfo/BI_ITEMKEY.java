package com.gis.monitor.pojo.basicinfo;

public class BI_ITEMKEY {
    private String ID;
    private String ITEMID;
    private String ITEMNAME;
    private String KEYWORD;


    public BI_ITEMKEY(String ID,String ITEMID,String ITEMNAME,String KEYWORD){
        this.ID = ID;
        this.ITEMID = ITEMID;
        this.ITEMNAME = ITEMNAME;
        this.KEYWORD = KEYWORD;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getITEMID() {
        return ITEMID;
    }

    public void setITEMID(String ITEMID) {
        this.ITEMID = ITEMID;
    }

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }
    
    public String getKEYWORD() {
        return KEYWORD;
    }

    public void setKEYWORD(String KEYWORD) {
        this.KEYWORD = KEYWORD;
    }





}
