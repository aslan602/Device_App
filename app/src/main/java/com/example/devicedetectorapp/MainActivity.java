package com.example.devicedetectorapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity contains
 * onCreate: creates new thread
 * onPause:  stops program with tearDown - still needs to be programmed
 * onResume: resumes program / restart discovery
 * onDestroy:  stops the program
 * tearDown: needs to be moved to nsdHelper
 */

public class MainActivity extends AppCompatActivity {
    public NetworkThread nt;

    /**
     * Creates new thread in NetworkThread and runs new thread.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(nt);
        thread.start();
    }

    /**
     * Needs to be implemented
     * Stops program with tearDown
     */
    @Override
    protected void onPause() {
        if (nsdHelper != null) {
            //nsdHelper.tearDown();
        }
        super.onPause();
    }

    /**
     * onResume: resumes/ restart discovery
     * need to figure out ndsHelper, registerService, and discoverServices
     */

    @Override
    protected void onResume() {
        super.onResume();
        if (nsdHelper != null) {
            //nsdHelper.registerService(connection.getLocalPort());
            //nsdHelper.discoverServices();
        }
    }
    /**
     * onDestroy: stops the program
     */

    @Override
    protected void onDestroy() {
        /*
        nsdHelper.tearDown();
        connection.tearDown();
        super.onDestroy();
         */
    }

    /**
     * Move to NSDHelper
     * tearDown: stops services and discovery
     */
    // NsdHelper's tearDown method
    public void tearDown() {
        //nsdManager.unregisterService(registrationListener);
        //nsdManager.stopServiceDiscovery(discoveryListener);
    }

}
