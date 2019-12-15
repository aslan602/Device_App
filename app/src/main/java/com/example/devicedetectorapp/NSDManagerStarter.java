package com.example.devicedetectorapp;


import android.net.nsd.NsdManager;

public class NSDManagerStarter {
    private String SERVICE_TYPE = "_http._tcp.";
    private NsdManager.DiscoveryListener discoveryListener;

    /** starts NSDManager service */
    public void startNSDManager(NsdManager nsdManager) {
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }
}
