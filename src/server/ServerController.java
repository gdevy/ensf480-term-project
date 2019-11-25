package server;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class ServerController {
    private int serverPort = 9000;
    protected ServerSocket serverSocket = null;

    public ServerController(int serverPort) {
        this.serverPort = serverPort;
        try {
            this.serverSocket = new ServerSocket(serverPort);

        } catch (IOException e) {
            //TODO server socket failure
        }
    }

//    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(Constants.MAX_THREADS);
//        ServerController server = new ServerController(9000);
//
//        while (true) {
//            Runnable serverController;
//            try {
//                if ((args.length != 0) && args[0].compareTo("commands") == 0) {
//                    serverController = new SocketController(server.serverSocket.accept());
//                } else {
//                    System.err.println("Making an echo server. Run with \"commands\" for real one");;
//                    serverController = new EchoSocketController(server.serverSocket.accept());
//                }
//                System.out.println("connection accepted \n");
//
//            } catch (IOException e) {
//                continue;
//                //TODO failed to accept()
//            }
//            threadPool.execute(serverController);
//        }
//    }
}

