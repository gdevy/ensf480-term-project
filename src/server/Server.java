package server;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Server {
    private int serverPort = 9000;
    protected ServerSocket serverSocket = null;

    public Server(int serverPort) {
        this.serverPort = serverPort;
        try {
            this.serverSocket = new ServerSocket(serverPort);

        } catch (IOException e) {
            //TODO server socket failure
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(Constants.MAX_THREADS);
        Server server = new Server(9000);

        while (true) {
            Runnable clientConnection;
            try {
                if (args[0].compareTo("commands") == 0) {
                    clientConnection = new CommandsClientConnection(server.serverSocket.accept());
                } else {
                    System.err.println("Making an echo server. Run with \"commands\" for real one");;
                    clientConnection = new EchoClientConnection(server.serverSocket.accept());
                }
                System.out.println("connection accepted \n");

            } catch (IOException e) {
                continue;
                //TODO failed to accept()
            }
            threadPool.execute(clientConnection);
        }
    }
}

