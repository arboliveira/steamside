package br.com.arbo.steamside.favorites;

import br.com.arbo.steamside.types.CollectionName;

public interface FavoritesOfUser {

	CollectionName favorites() throws NotSet;
}
