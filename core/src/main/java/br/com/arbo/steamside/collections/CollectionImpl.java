package br.com.arbo.steamside.collections;

import br.com.arbo.steamside.types.CollectionName;

public class CollectionImpl implements CollectionI {

	public static CollectionImpl clone(CollectionI in)
	{
		final CollectionImpl clone =
				new CollectionImpl(in.name(), in.isSystem());
		return clone;
	}

	public CollectionImpl(CollectionName name, IsSystem system) {
		this.name = name;
		this.system = system;
	}

	@Override
	public IsSystem isSystem()
	{
		return system;
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

	private final IsSystem system;
}
