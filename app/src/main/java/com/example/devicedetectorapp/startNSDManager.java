package com.example.devicedetectorapp;


/** starts NSDManager service */
public void startNSDManager() {
        nsdManager.discoverServices(
        SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
        }