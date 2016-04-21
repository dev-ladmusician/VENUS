package com.kim.ladmusician.venus.mqtt;

/**
 * Created by ladmusician on 16. 2. 19..
 */
public class MqttData {
    private int ERROR;
    private int TYPE;
    private String MAC_ADDR;
    private String TARGET;
    private String OPERATION;
    private String REQUEST_TYPE;
    private String BTN;
    private String MESSAGE;

    public MqttData(int ERROR) {
        this.ERROR = ERROR;
    }

    public MqttData(String macAddr, String btn, String message, int error) {
        this.MAC_ADDR = macAddr;
        this.BTN = btn;
        this.MESSAGE = message;
        this.ERROR = error;
    }

    public MqttData(int ERROR, int TYPE, String MAC_ADDR, String TARGET, String OPERATION, String REQUEST_TYPE, String BTN, String MESSAGE) {
        this.ERROR = ERROR;
        this.TYPE = TYPE;
        this.MAC_ADDR = MAC_ADDR;
        this.TARGET = TARGET;
        this.OPERATION = OPERATION;
        this.REQUEST_TYPE = REQUEST_TYPE;
        this.BTN = BTN;
        this.MESSAGE = MESSAGE;
    }

    public int getERROR() {
        return ERROR;
    }

    public void setERROR(int ERROR) {
        this.ERROR = ERROR;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getMAC_ADDR() {
        return MAC_ADDR;
    }

    public void setMAC_ADDR(String MAC_ADDR) {
        this.MAC_ADDR = MAC_ADDR;
    }

    public String getTARGET() {
        return TARGET;
    }

    public void setTARGET(String TARGET) {
        this.TARGET = TARGET;
    }

    public String getOPERATION() {
        return OPERATION;
    }

    public void setOPERATION(String OPERATION) {
        this.OPERATION = OPERATION;
    }

    public String getREQUEST_TYPE() {
        return REQUEST_TYPE;
    }

    public void setREQUEST_TYPE(String REQUEST_TYPE) {
        this.REQUEST_TYPE = REQUEST_TYPE;
    }

    public String getBTN() {
        return BTN;
    }

    public void setBTN(String BTN) {
        this.BTN = BTN;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
