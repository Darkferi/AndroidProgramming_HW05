
package hangmanserver.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameServer {
    
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_IDLE = 600000;
    private ServerSocket serverSocket;
    private Socket playerSocket;
    private ExecutorService executor;
    //private Thread serverCommand;
        
    public void serve(int port, int maxPlayer){
        try{
            serverSocket = new ServerSocket(port);
            playerSocket = new Socket();
            executor = Executors.newFixedThreadPool(maxPlayer);
            System.out.println("Waiting for Players!!!");
            
            while(true){
                playerSocket = serverSocket.accept();
                playerSocket.setSoLinger(true, LINGER_TIME);
                playerSocket.setSoTimeout(TIMEOUT_IDLE);
                Thread playerThread = new Thread(new GameHandler(playerSocket));
                playerThread.setPriority(Thread.MAX_PRIORITY);
                executor.execute(playerThread);
               // serverCommand = new Thread (new ServerCommand(serverSocket, playerSocket));
               // serverCommand.start();
            }
        } catch (IOException e){
            System.out.println("GameServer: IOException occured"
                    + "(possible reason: server run already)");
        } finally{
            if (executor != null){
                executor.shutdown();
            }
        }
    }
    
}
    