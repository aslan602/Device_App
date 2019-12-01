package com.example.devicedetectorapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

    /**
     * registerService: registers devices found on WiFi
     * gets on WiFi and registers the service and the port through serviceInfo class
     */
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName("Device_Detector");
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(port);

        /** gets nsdManager services variables*/
        nsdManager = Context.getSystemService(Context.NSD_SERVICE);

        /** starts registerService (built in class) writes */
        nsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);
    }