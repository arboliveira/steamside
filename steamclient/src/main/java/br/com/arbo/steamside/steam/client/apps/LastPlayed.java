package br.com.arbo.steamside.steam.client.apps;

import org.eclipse.jdt.annotation.NonNull;

public interface LastPlayed {

	@NonNull
	String lastPlayedOrCry() throws NeverPlayed;

}
