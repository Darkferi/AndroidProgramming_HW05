/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.darkferi.hw05.client.network;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author darkferi
 */
public class ClientListener implements Runnable {
        private final OutputHandler outputHandler;
        private final BufferedReader fromServer;
        private String str;
        public ClientListener(OutputHandler outputHandler,BufferedReader fromServer) {
            this.outputHandler = outputHandler;
            this.fromServer = fromServer;
        }

        @Override
        public void run(){
            try {

                for (;;) {
                    str = fromServer.readLine();
                    outputHandler.messageOnScreen(str);
                }
            } catch (IOException e) {
                System.out.println("Client(ClientListener): Lost Connection!!!");
            } catch (NullPointerException e){
                System.out.println("Client(ClientListener): NullPointerException occured!");
            }
        }

}