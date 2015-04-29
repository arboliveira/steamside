package br.com.arbo.steamside.steam.client.apps;

import java.util.Optional;
import java.util.function.Consumer;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public interface App extends LastPlayed {

	AppId appid();

	String executable() throws NotAvailableOnThisPlatform;

	void forEachCategory(final Consumer<SteamCategory> visitor);

	default boolean isGame()
	{
		try
		{
			return type().isGame();
		}
		catch (MissingFrom_appinfo_vdf e)
		{
			return false;
		}
	}

	boolean isInCategory(final SteamCategory category);

	Optional<String> lastPlayed();

	AppName name() throws MissingFrom_appinfo_vdf;

	AppType type() throws MissingFrom_appinfo_vdf;

}
