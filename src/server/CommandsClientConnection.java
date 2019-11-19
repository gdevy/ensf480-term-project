package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class CommandsClientConnection extends ClientConnection {
    public CommandsClientConnection(Socket clientSocket) {
        super(clientSocket);
    }


    @Override
    public void run() {
        ServerCommand command;
        List<String> strings;
        while (true) {

            try {
                strings = readFromSocket();
                command = ServerCommand.getServerCommand(strings.get(0));

                switch (command) {
                    case logout:
                        System.out.println("logout command received");
                        break;
                    case login:
                        System.out.println("login command received");
                        break;
                    case search:
                        System.out.println("seach command received");
                        break;
                    case invalid:
                    default:
                        System.out.println("invalid command");
                }

                writeToSocket("got " + command.getText());
            } catch (SocketException e) {
                System.err.println("client left\n");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
