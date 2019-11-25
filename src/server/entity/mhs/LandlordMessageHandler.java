package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LandlordMessageHandler extends MessageHandlerStrategy
{

	private String username;

	public LandlordMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois, String username )
	{
		super( sc, oos, ois );
		this.username = username;
	}

	@Override
	public void handleMessage( MessageType msgType )
	{
		try
		{
	        System.out.println( "Landlord handling " + msgType );
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