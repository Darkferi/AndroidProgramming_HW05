/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.darkferi.hw05.client.controller;

import java.io.IOException;
import com.example.darkferi.hw05.client.network.ClientSocketHandling;
import com.example.darkferi.hw05.client.network.OutputHandler;


public class ClientController {
    
    private final ClientSocketHandling clientSocket = new ClientSocketHandling();
    private int sPort;
    private OutputHandler outHandler;
    private String cmd;


    public void connect(int serverPort, OutputHandler screenHandler) {
        sPort = serverPort;
        outHandler = screenHandler;
        new Thread(new Runnable() {
            public void run() {
                clientSocket.connect(sPort, outHandler);
            }
        }).start();
    }

    public void exit() {
        clientSocket.disconnect();
    }

    
    public void sendMessage(String command) throws IOException {
        cmd = command;
        new Thread(new Runnable() {
            public void run() {
                 clientSocket.sendMessage(cmd);
            }
        }).start();
    }    
}

