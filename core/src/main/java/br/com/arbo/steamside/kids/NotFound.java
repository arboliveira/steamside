package br.com.arbo.steamside.kids;

public class NotFound extends RuntimeException
{

	public static NotFound name(String name)
	{
		return new NotFound("No kid with name: " + name);
	}

	public static NotFound user(final String user)
	{
		return new NotFound("No kid with username: " + user);
	}

	private NotFound(final String message)
	{
		super(message);
	}
}