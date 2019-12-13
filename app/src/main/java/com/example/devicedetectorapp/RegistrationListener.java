package com.example.devicedetectorapp;

public class RegistrationListener implements RegistrationListenerInterface {
    /**
     * initializeRegistrationListener(): callback function checks to see what succeeded and failed in
     * device registration - part of NSDHelper
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

            /**
             * throws error code for failed registration
             */
            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed. Put debugging code here to determine why.
                Log.e(REGISTER_TAG, "Failed to Unregister with code " + errorCode);
                Log.e(REGISTER_TAG, "and Failed Unregistered Service " + serviceInfo);
            }
        };
    }
}