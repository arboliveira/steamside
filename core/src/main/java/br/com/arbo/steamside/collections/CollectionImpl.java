package br.com.arbo.steamside.collections;

import br.com.arbo.steamside.types.CollectionName;

public class CollectionImpl implements CollectionI
{

	public static CollectionImpl clone(CollectionI in)
	{
		return new CollectionImpl(in.name());
	}

	public CollectionImpl(CollectionName name)
	{
		this.name = name;
	}

	@Override
	public CollectionName name()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name.toString();
	}

	private final CollectionName name;
}
