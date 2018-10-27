package br.com.arbo.steamside.steam.client.internal.library;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.library.Library;

public class LibraryImpl implements Library
{

	@Inject
	public LibraryImpl(SteamClientHome steamClientHome)
	{
		this.steamClientHome = steamClientHome;
	}

	private final SteamClientHome steamClientHome;

}
