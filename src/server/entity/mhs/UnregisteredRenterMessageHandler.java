package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;
import descriptor.LoginInfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UnregisteredRenterMessageHandler extends MessageHandlerStrategy
{

	public UnregisteredRenterMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois )
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
	            case PROPERTY_SEARCH_REQUEST:
	                PropertySearchCriteria psc = (PropertySearchCriteria) ois.readObject();
	                System.out.println( "Min bathrooms: " + psc.getMinBathrooms() );
	                System.out.println( "Furnished: " + psc.getFurnished() );
	                break;

	            case LOGIN_ATTEMPT:
	            	LoginInfo login = (LoginInfo) ois.readObject();
	            	System.out.println( "Username: " + login.username );
	            	System.out.println( "Password: " + login.password );
	            	break;

	            default:
	                System.out.println( "Message handler can't handle: " + msgType );
	                ois.readObject();
	                break;
	        }
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}