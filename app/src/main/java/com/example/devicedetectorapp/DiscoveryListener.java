package com.example.devicedetectorapp;

public class DiscoveryListener implements DiscoveryListenerInterface {
        public void initializeDiscoveryListener() {

                /** Instantiate a new DiscoveryListener */
                discoveryListener = new NsdManager.DiscoveryListener() {

                        /** Called as soon as service discovery begins. */
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

                        /** If WiFi is Lost, create log of service lost*/
                        @Override
                        public void onServiceLost(NsdServiceInfo service) {
                                // When the network service is no longer available.
                                // Internal bookkeeping code goes here.
                                Log.e(TAG, "service lost: " + service);
                                }
                        /** If discovery is stopped throw error log*/
                        @Override
                        public void onDiscoveryStopped(String serviceType) {
                                Log.i(TAG, "Discovery stopped: " + serviceType);
                                }
                        /** Log error thrown when StartDiscovery fails */
                        @Override
                        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                                nsdManager.stopServiceDiscovery(this);
                                }
                        /** Log error thrown when StopDiscovery fails */
                        @Override
                        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                                nsdManager.stopServiceDiscovery(this);
                        }
                };
        }
}