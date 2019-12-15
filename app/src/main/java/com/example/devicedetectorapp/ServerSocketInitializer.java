package com.example.devicedetectorapp;

import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketInitializer {
    private ServerSocket serverSocket;
    /** gets available port number and applies to ServerSocket */
    public int initializeServerSocket() {
        /** Initialize a server socket on the next available port. */
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** Store the chosen port. */
        return serverSocket.getLocalPort();
    }
}
