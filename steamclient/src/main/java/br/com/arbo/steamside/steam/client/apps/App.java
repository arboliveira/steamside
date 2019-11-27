package br.com.arbo.steamside.steam.client.apps;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

public interface App
{

	AppId appid();

	Optional<String> executable(Platform platform);

	Map<String, String> executables();

	void forEachCategory(final Consumer<SteamCategory> visitor);

	boolean isInCategory(final SteamCategory category);

	boolean isOwned();

	Optional<LastPlayed> lastPlayed();

	boolean matches(AppCriteria c);

	AppName name();

	AppType type();

}
