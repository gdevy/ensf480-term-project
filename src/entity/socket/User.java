package entity.socket;

import descriptor.UserTypeLogin;

public class User
{
	public UserTypeLogin userType;
	public String email;

	public User( UserTypeLogin userType, String email )
	{
		this.userType = userType;
		this.email = email;
	}
}