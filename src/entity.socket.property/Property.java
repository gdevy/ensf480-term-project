package entity.socket.property;

import descriptor.Address;
import java.io.Serializable;

public class Property implements Serializable
{
	private Address address;
	private Quadrant quadrant;
	private PropertyStatus status;
	private PropertyTraits traits;
	private int id;

	private static final long serialVersionUID = 3L;

	static private int count = 0;

	public Property( Address address, Quadrant quadrant, PropertyStatus status, PropertyTraits traits )
	{
		this.address = address;
		this.quadrant = quadrant;
		this.status = status;
		this.traits = traits;
		
		this.id = 1000 + count;
		count++;
	}

}