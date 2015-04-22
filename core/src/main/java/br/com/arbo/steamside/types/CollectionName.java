package br.com.arbo.steamside.types;

public class CollectionName {

	public CollectionName(String value)
	{
		this.value = value;
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
