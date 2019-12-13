package com.example.devicedetectorapp;

public class NSDManagerStarter {
    /** starts NSDManager service */
    public void startNSDManager() {
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }
}
