package br.com.arbo.steamside.types;

import org.eclipse.jdt.annotation.NonNull;

public class CollectionName {

	public CollectionName(@NonNull String value)
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

	public final @NonNull String value;

}
