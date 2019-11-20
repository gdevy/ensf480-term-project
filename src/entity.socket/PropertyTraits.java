package entity.socket;

class PropertyTraits
{
	private PropertyType type;
	private int bedrooms;
	private int bathrooms;
	private double squareFootage;
	private boolean furnished;

	public PropertyTraits( PropertyType type, int bedrooms, int bathrooms, double squareFootage, boolean furnished )
	{
		this.type = type;
		this.bedrooms = bedrooms;
		this.bathrooms = bathrooms;
		this.squareFootage = squareFootage;
		this.furnished = furnished;
	}

}