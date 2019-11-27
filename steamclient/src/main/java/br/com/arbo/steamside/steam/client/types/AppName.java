package br.com.arbo.steamside.steam.client.types;

import java.util.Objects;

public class AppName
{

	public String name()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name();
	}

	public AppName(String name)
	{
		this.name = Objects.requireNonNull(name);
	}

	private final String name;
}
