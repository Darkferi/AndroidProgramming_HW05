/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.darkferi.hw05.client.network;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author darkferi
 */
public class ClientSocketHandling {
    private Socket clientSocket; 
    private InetSocketAddress serverPort;
    private static final int ESTABLISHING_TIMEOUT = 10000;
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_IDLE = 600000;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Thread listenerThread;
    private final boolean autoFlush = true;
    private boolean connected = false;
    private static final String EXIT = "exit";
    private static final String IP = "192.168.0.101";
            //"10.0.2.2";
            //"192.168.0.101";
    
    
    public void connect(int port, OutputHandler screenHandler){
        try{
            //creating client socket to request for TCP connection
            clientSocket = new Socket();
            clientSocket.setSoLinger(true, LINGER_TIME);
            clientSocket.setSoTimeout(TIMEOUT_IDLE);
            //server port is 8080
            serverPort = new InetSocketAddress(IP, port);
            //sending the request for the connection
            clientSocket.connect(serverPort,ESTABLISHING_TIMEOUT);
            connected = true;
            toServer = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
            fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            listenerThread = new Thread(new ClientListener(screenHandler, fromServer));
            listenerThread.start();
                        
        } catch (IllegalArgumentException e){
            System.out.println("Socket Argument Problem in connect()!!");
        } catch (SocketTimeoutException e){
            System.out.println("Timeout expired before connecting!!");
        } catch(IOException e){
            System.out.println("connect(): IOException (Server is not listening)!!");
        }
    }
    
    public void disconnect(){
        try{
            if (connected){
                toServer.println(EXIT);
                clientSocket.close();
                connected = false;
            }
            else{
                System.out.println("EXIT from command line!");
            }
        } catch(IOException e){
            System.out.println("CliendSocket > disconnect() > IOException!!");
        }
    }
    
    public void sendMessage(String charGuess){
        if(connected){
            toServer.println(charGuess);
        }
        else{
            System.out.println("You have not started the game!!! First start!!!!");
        }
    }

}
