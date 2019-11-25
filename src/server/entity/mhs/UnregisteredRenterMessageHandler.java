package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;
import descriptor.*;
import server.entity.mhs.*;

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

	            	oos.writeObject( MessageType.LOGIN_RESULT );
	            	UserTypeLogin userType = UserTypeLogin.REGISTERED_RENTER;
	            	oos.writeObject( userType );
	            	System.out.println( "Sent login validation" );

	            	MessageHandlerStrategy mhs;
	            	switch( userType )
	            	{
						case LOGIN_FAILED:
							break;
						case REGISTERED_RENTER:
							mhs = new RegisteredRenterMessageHandler( sc, oos, ois, login.username );
							sc.setMessageHandler( mhs );
							break;
						case LANDLORD:
							mhs = new LandlordMessageHandler( sc, oos, ois, login.username );
							sc.setMessageHandler( mhs );
							break;
						case MANAGER:
							System.out.println( "*** I CAN'T HANDLE MANAGER LOGINS YET" );
							break;

	            	}

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