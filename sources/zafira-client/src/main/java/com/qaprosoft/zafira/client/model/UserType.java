package com.qaprosoft.zafira.client.model;


public class UserType extends AbstractType
{
	private String userName;
	private String email;
	private String firstName;
	private String lastName;
	
	public UserType(String userName)
	{
		this.userName = userName;
	}

	public UserType(String userName, String email, String firstName, String lastName)
	{
		this.userName = userName;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}
