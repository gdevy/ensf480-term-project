package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RegisteredRenterMessageHandler extends MessageHandlerStrategy
{

	private String username;

	public RegisteredRenterMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois, String username )
	{
		super( sc, oos, ois );
		this.username = username;
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

	            case VIEW_SAVED_SEARCHES_REQUEST:
	            	ois.readObject();
	            	ArrayList<PropertySearchCriteria> savedSearches = new ArrayList<PropertySearchCriteria>();

	            	PropertySearchCriteria search = new PropertySearchCriteria();
					search.setMaxMonthlyRent( 2000 );
					search.setMinBathrooms( 2 );
					search.addQuadrant( Quadrant.NW );
					search.addQuadrant( Quadrant.SW );
					search.addType( PropertyType.HOUSE );
					search.addType( PropertyType.APARTMENT );
					search.setFurnished( true );

					savedSearches.add(search);
					savedSearches.add(search);
					oos.writeObject( MessageType.VIEW_SAVED_SEARCHES_RESULT );
					oos.writeObject( savedSearches );
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