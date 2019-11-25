package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import entity.socket.property.*;
import server.SocketController;
import descriptor.*;

import java.util.ArrayList;
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

	            case VIEW_LANDLORD_PROPERTIES_REQUEST:
	            	ois.readObject();
	            	ArrayList<Property> currentProperties = new ArrayList<Property>();

	            	PropertyTraits pt = new PropertyTraits( PropertyType.HOUSE, 1, 1, 1000, true );
        			Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
        			Property tempp = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );

					currentProperties.add(tempp);
					currentProperties.add(tempp);
					oos.writeObject( MessageType.VIEW_LANDLORD_PROPERTIES_RESULT );
					oos.writeObject( currentProperties );
					System.out.println( "Sent back landlord's properties" );
					break;

	            default:
	            	ois.readObject();
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