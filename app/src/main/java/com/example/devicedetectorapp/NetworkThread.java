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

    /**
     * initializeRegistrationListener(): callback function checks to see what succeeded and failed in
     * device registration - part of NSDHelper
     *
     */
    public void initializeRegistrationListener() {
        registrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                /**
                 * Save the service name. Android may have changed it in order to
                 * resolve a conflict, so update the name you initially requested
                 * with the name Android actually used.
                 */
                serviceName = NsdServiceInfo.getServiceName();
                Log.d(REGISTER_TAG, "Registration Successful");
                Log.d(REGISTER_TAG, "The registered name is " + serviceName);
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                /** Registration failed!debugging code here to determine why. Could be log for debugging.*/
                Log.e(REGISTER_TAG, "Registration Failed with code " + errorCode);
                Log.e(REGISTER_TAG, "and Failed Service " + serviceInfo);
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                /** Only called if service is unregistered */
                /**
                * Service has been unregistered. This only happens when you call
                * NsdManager.unregisterService() and pass in this listener.
                */
                Log.d(REGISTER_TAG, "Service is Unregistered.");
            }
            /** throws error code for failed registration */
            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed. Put debugging code here to determine why.
                Log.e(REGISTER_TAG, "Failed to Unregister with code " + errorCode);
                Log.e(REGISTER_TAG, "and Failed Unregistered Service " + serviceInfo);
            }
        };
    }
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


    public void startNSDManager() {
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }


    public void initializeServerSocket() {

        // Initialize a server socket on the next available port.
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Store the chosen port.
        localPort = serverSocket.getLocalPort();
    }


    public void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        discoveryListener = new NsdManager.DiscoveryListener() {

            // Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            /** Finds Service (device connects to the network)
             * creates logs and sends to resolveService
             */
            @Override
            public void onServiceFound(NsdServiceInfo service) {
                /** A service was found! Do something with it. */
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    /** Service type is the string containing the protocol and
                    * transport layer for this service.
                     */
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(serviceName)) {
                    /** The name of the service tells the user what they'd be
                    * connecting to. It could be "Bob's Chat App".
                     */
                    Log.d(TAG, "Same machine: " + serviceName);
                } else if (service.getServiceName().contains("NsdChat")) {
                    /** Sends to resolveService */
                    nsdManager.resolveService(service, resolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(TAG, "service lost: " + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }
        };
    }


    public void initializeResolveListener() {
        resolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails. Use the error code to debug.
                Log.e(TAG, "Resolve failed: " + errorCode);
            }
            /**
             * enter into new class, add port number, ip address
             * get more information from nsdServiceInfo -- built in class
             * -dates, Operating System
             *
             * -connect to Scan device class
             *
             * onServiceResolved:
             * End of scan process to get data on service name and port number.
             */
            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(serviceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
                nsdHelper helper = new nsdHelper();
                helper.setPort(port);
                helper.setHost(host);

            }
        };
    }

}
