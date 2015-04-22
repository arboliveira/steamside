package br.com.arbo.steamside.collections;

import java.util.Optional;

public class InMemoryFavoriteHome {

	{
		clear();
	}

	public CollectionImpl get() throws FavoriteNotSet
	{
		return favorite.orElseThrow(FavoriteNotSet::new);
	}

	public void set(CollectionImpl stored)
	{
		favorite = Optional.of(stored);
	}

	public void unset(CollectionImpl stored)
	{
		favorite
		.filter(value -> value == stored)
		.ifPresent(value -> clear());
	}

	private void clear()
	{
		favorite = Optional.empty();
	}

	private Optional<CollectionImpl> favorite;
}
