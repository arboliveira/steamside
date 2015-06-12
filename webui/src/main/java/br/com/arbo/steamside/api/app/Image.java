package br.com.arbo.steamside.api.app;

import br.com.arbo.steamside.steam.client.types.AppId;

public class Image
{

	public static String image(AppId appid)
	{
		return "http://cdn.akamai.steamstatic.com/steam/apps/"
			+ appid
			+ "/header.jpg";
	}

}
