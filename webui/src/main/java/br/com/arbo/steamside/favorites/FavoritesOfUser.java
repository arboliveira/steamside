package br.com.arbo.steamside.favorites;

import java.util.Optional;

import br.com.arbo.steamside.types.CollectionName;

public interface FavoritesOfUser
{

	Optional<CollectionName> favorites();
}
