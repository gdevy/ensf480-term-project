package client;

import server.Constants;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.in;
import static server.Constants.PORT_NUM;

enum Test {
    test1,
    test2
}

public class TextClient {
    private Socket socket;
    private BufferedReader fromServer;
    private BufferedReader userInput;
    private PrintWriter toServer;

    TextClient(String hostIP) throws IOException {
        socket = new Socket(hostIP, PORT_NUM);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream(), true);
        userInput = new BufferedReader(new InputStreamReader(System.in));
    }


    public static void main(String[] args) {

        String ipAddress = "127.0.0.1";
        TextClient client = null;

        List<List<String>> list1 = new ArrayList<>() {
            {
                add(new ArrayList<>() {
                    {
                        add("LOGI");
                        add("Greg");
                    }
                });
                add(new ArrayList<>() {
                    {
                        add("SEAR");
                        add("some property");
                    }

                });
            }
        };

        List<List<String>> list2 = new ArrayList<>() {
            {
                add(new ArrayList<>() {
                    {
                        add("LOGI");
                        add("Erslan");
                    }
                });
                add(new ArrayList<>() {
                    {
                        add("SEAR");
                        add("some property");
                    }

                });
            }
        };

        Iterator test = null;
        if (args[0].compareTo("test1") == 0) {
            System.err.println("running test 1");
            test = list1.iterator();
        } else if (args[0].compareTo("test2") == 0) {
            System.err.println("running test 2");
            test = list1.iterator();
        } else {
            System.err.println("running text based");
            //leave test null
        }

        try {
            client = new TextClient(ipAddress);
        } catch (IOException e) {
            System.err.println("Connecting to " + ipAddress + " failed \n");
            exit(1);
        }

        List<String> userInput;
        List<String> serverResponse;
        while (true) {
            try {
                if ((test != null) && test.hasNext()) {
                    userInput = (List<String>) test.next();
                } else {
                    userInput = client.getUserInput();
                }
                client.writeToServer(userInput);
                System.out.printf("Server: \n");
                serverResponse = client.readFromServer();
                for (String line : serverResponse) {
                    System.out.printf("%s\n", line);
                }
            } catch (IOException e) {
                System.err.println("Error communicating with server\n");
                exit(2);

            }
        }
    }

    List<String> getUserInput() throws IOException {   //dont change even though now it looks redundant
        List<String> input = new ArrayList<>();
        String line;

        while ((line = userInput.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            input.add(line);
        }
        return input;
    }

    void writeToServer(List<String> toWrite) throws IOException {

        for (String line : toWrite) {
            toServer.printf("%s\n", line);
        }
        toServer.printf("\n");

    }

    List<String> readFromServer() throws IOException {    //dont change even though now it looks redundant

        List<String> input = new ArrayList<>();
        String line;
        while ((line = fromServer.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            input.add(line);
        }

        return input;
    }
}
