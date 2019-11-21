package entity.socket.property;

import java.io.Serializable;

public class PropertyTraits implements Serializable
{
	private PropertyType type;
	private int bedrooms;
	private int bathrooms;
	private double squareFootage;
	private boolean furnished;

	private static final long serialVersionUID = 2L;

	public PropertyTraits( PropertyType type, int bedrooms, int bathrooms, double squareFootage, boolean furnished )
	{
		this.type = type;
		this.bedrooms = bedrooms;
		this.bathrooms = bathrooms;
		this.squareFootage = squareFootage;
		this.furnished = furnished;
	}

}