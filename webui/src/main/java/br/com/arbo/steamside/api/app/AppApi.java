package br.com.arbo.steamside.api.app;

import br.com.arbo.steamside.steam.client.apps.LastPlayed;
import br.com.arbo.steamside.steam.client.types.AppId;

public interface AppApi extends LastPlayed {

	AppId appid();

	default String image()
	{
		return "http://cdn.akamai.steamstatic.com/steam/apps/"
				+ appid()
				+ "/header.jpg";
	}

	String name();

	default String run_api_link()
	{
		return String.format(
				"%s/%s/%s/%s",
				br.com.arbo.steamside.mapping.Api.api,
				br.com.arbo.steamside.mapping.App.app,
				appid(),
				br.com.arbo.steamside.mapping.App.run);
	}

	default String store()
	{
		return "http://store.steampowered.com/app/" + appid();
	}

	boolean unavailable();

}
