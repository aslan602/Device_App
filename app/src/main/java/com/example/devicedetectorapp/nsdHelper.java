package com.example.devicedetectorapp;

import java.net.InetAddress;

public class nsdHelper {
    int port;
    InetAddress host;

    nsdHelper() {
        this.port = 0;
        this.host = null;
    }

    nsdHelper(int port, InetAddress host) {
        this.port = port;
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHost(InetAddress host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public InetAddress getHost() {
        return this.host;
    }

    public void registerService(int port) {
        NetworkThread.registerService(port);
    }

    public void discoverServices() {
        NetworkThread.startNSDManager();
    }


}
