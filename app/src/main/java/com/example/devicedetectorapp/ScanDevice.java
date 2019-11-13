package com.example.devicedetectorapp;

public class ScanDevice {
    private String name;
    private String type;
    private String ipAddress;
    private String os;
    private int installDate;
    private int replaceDate;

    public ScanDevice() {
        name = null;
        type = null;
        ipAddress = null;
        os = null;
        installDate = -1;
        replaceDate = -1;
    }

    public ScanDevice(String name, String type, String ipAddress, String os, int installDate, int replaceDate) {
        this.name = name;
        this.type = type;
        this.ipAddress = ipAddress;
        this.os = os;
        this.installDate = installDate;
        this.replaceDate = replaceDate;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public String getOs() {
        return os;
    }
    public int getInstallDate() {
        return installDate;
    }
    public int getReplaceDate() {
        return replaceDate;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public void setInstallDate(int installDate) {
        this.installDate = installDate;
    }
    public void setReplaceDate(int replaceDate) {
        this.replaceDate = replaceDate;
    }
}