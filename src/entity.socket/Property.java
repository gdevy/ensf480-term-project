package entity.socket;

import entity.descriptor.Address;

enum PropertyStatus
{
	AVAILABLE,
	SUSPENDED,
	RENTED,
	REMOVED
}

enum Quadrant
{
	NW,
	NE,
	SW,
	SE
}

enum PropertyType
{
	HOUSE,
	DUPLEX,
	TOWNHOUSE,
	APARTMENT,
	CONDO,
	MAINFLOOR,
	BASEMENT
}

public class Property
{
	private Address address;
	private Quadrant quadrant;
	private PropertyStatus status;
	private PropertyTraits traits;

	public Property( Address address, Quadrant quadrant, PropertyStatus status, PropertyTraits traits )
	{
		this.address = address;
		this.quadrant = quadrant;
		this.status = status;
		this.traits = traits;
	}

}