package com.example.devicedetectorapp;

public class ResolveListener implements ResolveListenerInterface {
    public void initializeResolveListener() {
        //resolveListener = new NsdManager.ResolveListener() {
        /** When resolve fails, throw error code */
        /*
        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            Log.e(TAG, "Resolve failed: " + errorCode);
        }
         */
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
        /*
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
        */
    }
}

