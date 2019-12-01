package com.example.devicedetectorapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * NetworkThread implements Runnable, RegistrationListener, Discovery Listener, and Resolve Listener
 *
 * Needed moves:
 * Registration in class (new file) -- Registration Listener (callback)
 * ServiceDiscovery in class (new file) -- DiscoveryListener (callback)
 * ResolveListener in class (new file) -- ResolveListener - need to figure how to save devices
 * (ScanDevice class)
 *
 * Interfaces implemented????
 *
 */

public class NetworkThread implements Runnable, RegistrationListener, DiscoveryListener, ResolveListener {

    private ServerSocket serverSocket;
    private int localPort;
    private String serviceName;
    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discoveryListener;
    private String SERVICE_TYPE = "_http._tcp.";
    private String REGISTER_TAG = "Registration:";
    private static final String DEBUG_TAG = "NetworkStatus";
    private MainActivity activity;
    private static final String TAG = "NSD Service";
    private NsdManager.RegistrationListener registrationListener;
    private NsdManager.ResolveListener resolveListener;
    public NsdServiceInfo mService;

    /**
     * run() runs starts MainActivity  in new thread
     * checks WiFi connection through for loop
     *
     * Need to break thread if WiFi is not available
     */
    public void run() {
        // Check to see if there is a WiFi connection.

        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);

        /**
         * if there is an available WiFi connection, start process of ServerSocket, registerService,
         * and NSDManager
         */
        if (isWifiConn == true) {
            initializeServerSocket(); /** Find available port number */
            registerService(localPort); /** Register port number on WiFi service */
            startNSDManager(); /** Starts service discovery */
        } else { /** Not on WiFi network break thread and throw error message */
            activity.runOnUiThread(new Runnable() {
                public void run() { /** update to this format: [name].interrupt()*/
                    Toast.makeText("WiFi connection not available.", this, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
