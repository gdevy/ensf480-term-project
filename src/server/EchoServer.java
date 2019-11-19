package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class EchoServer extends Server {

    public EchoServer(int serverPort) {
        super(serverPort);
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(Constants.MAX_THREADS);
        EchoServer server = new EchoServer(9000);

        while (true) {
            Runnable clientConnection;
            try {
                clientConnection = new ClientConnection(server.serverSocket.accept());

                System.out.println("connection accepted \n");

            } catch (IOException e) {
                continue;
                //TODO failed to accept()
            }
            threadPool.execute(clientConnection);
        }
    }
}

