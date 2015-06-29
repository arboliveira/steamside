package br.com.arbo.steamside.types;

import java.util.Objects;

public class CollectionName
{

	public CollectionName(String value)
	{
		this.value = Objects.requireNonNull(value, "Tag name can't be empty");
	}

	public boolean equalsCollectionName(CollectionName name)
	{
		return value.equals(name.value);
	}

	@Override
	public String toString()
	{
		return value;
	}

	public final String value;

}
