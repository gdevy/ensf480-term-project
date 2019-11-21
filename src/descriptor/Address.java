package descriptor;

public class Address
{
	private int streetNumber;
	private String street;
	private String city;
	private String province;
	private String postalCode;

	public Address( int streetNumber, String street, String city, String province, String postalCode )
	{
		this.streetNumber = streetNumber;
		this.street = street;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
	}

}