package client;
import server.SocketConnection;

import java.net.Socket;

public class ClientSocketConnection extends SocketConnection {
    public ClientSocketConnection(Socket clientSocket) {
        super(clientSocket);
    }

    
}
