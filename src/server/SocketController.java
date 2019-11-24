package server;

import entity.socket.MessageType;
import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import server.entity.mhs.*;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class SocketController implements Runnable
{

    private Socket sock;
    private ObjectInputStream sockIn;
    private ObjectOutputStream sockOut;

    private MessageHandlerStrategy messageHandler;

    public SocketController( Socket sock )
    {
        try
        {
            this.sock = sock;
            this.sockOut = new ObjectOutputStream( sock.getOutputStream() );
            this.sockIn = new ObjectInputStream( sock.getInputStream() );
            this.messageHandler = new UnregisteredRenterMessageHandler( this, sockOut, sockIn );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while( true )
            {
                MessageType msgType = (MessageType) sockIn.readObject();
                if( msgType != null ) messageHandler.handleMessage( msgType );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

    }

}