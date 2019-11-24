package server;

import entity.socket.MessageType;
import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class SocketController implements Runnable
{

    private Socket sock;
    private ObjectInputStream sockIn;
    private ObjectOutputStream sockOut;

    public SocketController( Socket sock )
    {
        try
        {
            System.out.println( "creating socketcontroller" );
            this.sock = sock;
            this.sockOut = new ObjectOutputStream( sock.getOutputStream() );
            this.sockIn = new ObjectInputStream( sock.getInputStream() );
            System.out.println( "created socketcontroller" );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        System.out.println( "running" );
        try
        {
            while( true )
            {
                MessageType msgType = (MessageType) sockIn.readObject();
                if( msgType != null ) handleNewMessage( msgType );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

    }

    private void handleNewMessage( MessageType msgType ) throws IOException, ClassNotFoundException
    {
        System.out.println( msgType + " message received" );
        switch( msgType )
        {
            case PROPERTY_SEARCH_REQUEST:
                PropertySearchCriteria psc = (PropertySearchCriteria) sockIn.readObject();
                System.out.println( "Min bathrooms: " + psc.getMinBathrooms() );
                System.out.println( "Furnished: " + psc.getFurnished() );
                ArrayList<PropertyType> al = psc.getTypes();
                System.out.println( "Property Types: " + al.get(0) + ", " + al.get(1) );
                break;
        }
    }

}