package br.com.arbo.steamside.favorites;

import br.com.arbo.steamside.types.SteamCategory;

public interface FavoritesOfUser {

	SteamCategory favorites() throws NotSet;

	public static class NotSet extends Exception {
		//
	}
}
