package com.example.devicedetectorapp;

import android.bluetooth.BluetoothClass;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DeviceTest {
    @Test
    public void isTheDeviceARealDeviceWithAnIPAdrress() {
        BluetoothClass.Device device = new BluetoothClass.Device();
        assertNotNull(device);
    }
}
