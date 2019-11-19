package server;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Server {
    protected int serverPort = 9000;
    protected ServerSocket serverSocket = null;

    public Server(int serverPort) {
        this.serverPort = serverPort;
        try {
            this.serverSocket = new ServerSocket(serverPort);

        } catch (IOException e) {
            //TODO server socket failure
        }
    }
}
