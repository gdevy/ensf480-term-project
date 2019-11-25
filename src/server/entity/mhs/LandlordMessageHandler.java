package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LandlordMessageHandler extends MessageHandlerStrategy
{

	public LandlordMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois )
	{
		super( sc, oos, ois );
	}

	@Override
	public void handleMessage( MessageType msgType )
	{
		try
		{
	        System.out.println( "Unregistered Renter handling " + msgType );
	        switch( msgType )
	        {
	            case CREATE_NEW_PROPERTY:
	            	Property p = (Property) ois.readObject();
	            	System.out.println( p.getTraits() );
	            	break;

	            default:
	                System.out.println( "Received unknown message type: " + msgType );
	                break;
	        }
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}