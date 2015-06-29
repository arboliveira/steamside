package br.com.arbo.steamside.kids;

public class DuplicateUser extends RuntimeException
{

	public DuplicateUser()
	{
		super("There's already a kid with this OS user account.");
	}

}
