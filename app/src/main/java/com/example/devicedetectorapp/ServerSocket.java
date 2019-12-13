package com.example.devicedetectorapp;

public class ServerSocket {
    /** gets available port number and applies to ServerSocket */
    public void initializeServerSocket() {
        /** Initialize a server socket on the next available port. */
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** Store the chosen port. */
        localPort = serverSocket.getLocalPort();
    }
}
