package br.com.arbo.opersys.username;

import java.util.Objects;

public class Username implements User
{

	public Username(String username)
	{
		this.username = Objects.requireNonNull(username);
	}

	@Override
	public String username()
	{
		return username;
	}

	private final String username;
}
