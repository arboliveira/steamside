package br.com.arbo.steamside.favorites;

import br.com.arbo.steamside.types.Category;

public interface FavoritesOfUser {

	Category favorites() throws NotSet;

	public static class NotSet extends Exception {
		//
	}
}
