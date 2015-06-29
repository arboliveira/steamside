package br.com.arbo.steamside.kids;

import java.util.Objects;

public class KidName
{

	public KidName(String name)
	{
		this.name = Objects.requireNonNull(name, "Kid name can't be empty");
	}

	public final String name;

}
