package br.com.arbo.steamside.kids;

import java.util.Objects;

public class KidName
{

	public KidName(String name)
	{
		this.name = Objects.requireNonNull(name);
	}

	public final String name;

}
