package com.gis.monitor.pojo.managegrade;


public class MG_SCOREMANAGER_DDZ {


    private String EVALUTIONPRO3;

    private String EVALUTIONPRO4;

    private Double MIN;

    private Double MAX;

    private Double SCORE;

    private Integer TYPE;


    public MG_SCOREMANAGER_DDZ(
            String EVALUTIONPRO3,
            String EVALUTIONPRO4,
            Double MIN, Double MAX,
            Double SCORE, Integer TYPE) {
        this.EVALUTIONPRO3 = EVALUTIONPRO3;
        this.EVALUTIONPRO4 = EVALUTIONPRO4;
        this.MIN = MIN;
        this.MAX = MAX;
        this.SCORE = SCORE;
        this.TYPE = TYPE;
    }

    public String getEVALUTIONPRO3() {
        return EVALUTIONPRO3;
    }

    public void setEVALUTIONPRO3(String EVALUTIONPRO3) {
        this.EVALUTIONPRO3 = EVALUTIONPRO3;
    }

    public String getEVALUTIONPRO4() {
        return EVALUTIONPRO4;
    }

    public void setEVALUTIONPRO4(String EVALUTIONPRO4) {
        this.EVALUTIONPRO4 = EVALUTIONPRO4;
    }

    public Double getMIN() {
        return MIN;
    }

    public void setMIN(Double MIN) {
        this.MIN = MIN;
    }

    public Double getMAX() {
        return MAX;
    }

    public void setMAX(Double MAX) {
        this.MAX = MAX;
    }

    public Double getSCORE() {
        return SCORE;
    }

    public void setSCORE(Double SCORE) {
        this.SCORE = SCORE;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }


    @Override
    public String toString() {
        return "MG_SCOREMANAGER_DDZ{" +
                ", EVALUTIONPRO3='" + EVALUTIONPRO3 + '\'' +
                ", EVALUTIONPRO4='" + EVALUTIONPRO4 + '\'' +
                ", MIN='" + MIN + '\'' +
                ", MAX='" + MAX + '\'' +
                ", SCORE='" + SCORE + '\'' +
                ", TYPE='" + TYPE + '\'' +
                //   ", ISLASTEST='" + ISLASTEST + '\'' +
                '}';
    }


}
