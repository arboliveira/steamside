package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import java.io.FileInputStream;
import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;

public class ExampleDumpAppCacheParse
{

	public static void main(final String[] args) throws IOException
	{
		try (FileInputStream f =
			new FileInputStream(
				new File_appinfo_vdf(SteamLocations
					.fromSteamPhysicalFiles()).appinfo_vdf()))
		{
			new Parse_appinfo_vdf(new Content_appinfo_vdf(f),
				(appid, appinfo) -> System.out.println(appid + "=" + appinfo))
					.parse();
		}
	}
}
