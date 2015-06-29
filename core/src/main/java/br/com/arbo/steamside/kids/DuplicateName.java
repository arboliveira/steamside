package br.com.arbo.steamside.kids;

public class DuplicateName extends RuntimeException
{

	public DuplicateName()
	{
		super("There's already a kid with this name.");
	}

}
