package descriptor;

public enum UserTypeLogin
{
	LOGIN_FAILED( "Login Failed" ),
	REGISTERED_RENTER( "Registered Renter" ),
	LANDLORD( "Landlord" ),
	MANAGER( "Manager" );

	private String name;

	private UserTypeLogin( String name )
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}