package entity.socket.property;

public enum PropertyType
{
	HOUSE("House"),
	DUPLEX("Duplex"),
	TOWNHOUSE("Townhouse"),
	APARTMENT("Apartment"),
	CONDO("Condo"),
	MAINFLOOR("Mainfloor"),
	BASEMENT("Basement");

	private String name;

	private PropertyType( String name )
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}