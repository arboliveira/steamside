package br.com.arbo.steamside.demo.context;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.internal.home.InMemorySteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

class DemoAppsHome
{

	static SteamClientHome newSteamClientHome()
	{
		return newInstance();
	}

	static class Appx
	{

		Appx()
		{
			lastPlayed = i--;
		}

		static long i = 1000;

		String appid;
		String categories;
		long lastPlayed;

		String name;

	}

	private static void add(InMemorySteamClientHome appsHome, Appx... appxs)
	{
		Stream.of(appxs).map(DemoAppsHome::app).forEach(appsHome::add);
	}

	private static App app(Appx appx)
	{
		return new AppImpl.Builder()
			.name(new AppName(appx.name))
			.appid(appx.appid)
			.type(AppType.GAME)
			.lastPlayed(new LastPlayed(String.valueOf(appx.lastPlayed)))
			.categories(
				StringUtils.split(
					Optional.ofNullable(appx.categories)
						.orElse("")))
			.make().get();
	}

	private static InMemorySteamClientHome newInstance()
	{
		InMemorySteamClientHome appsHome = new InMemorySteamClientHome();
		populate(appsHome);
		return appsHome;
	}

	private static void populate(InMemorySteamClientHome appsHome)
	{
		/* @formatter:off */
		add(appsHome,
			new Appx() {{
				name = "Half-Life";
				appid = "70";
				categories = "Valve";
			}},
			new Appx() {{
				name = "Portal";
				appid = "400";
				categories = "Valve";
			}},
			new Appx() {{
				name = "Grand Theft Auto V";
				appid = "271590";
			}},
			new Appx() {{
				name = "Windosill";
				appid = "37600";
			}},
			new Appx() {{
				name = "Counter-Strike: Global Offensive";
				appid = "730";
				categories = "Valve";
			}},
			new Appx() {{
				name = "The Elder Scrolls V: Skyrim";
				appid = "72850";
			}},
			new Appx() {{
				name = "Papers, Please";
				appid = "239030";
			}},
			new Appx() {{
				name = "Spec Ops: The Line";
				appid = "50300";
			}},
			new Appx() {{
				name = "Scribblenauts Unlimited";
				appid = "218680";
			}},
			new Appx() {{
				name = "LEGO MARVEL Super Heroes";
				appid = "249130";
			}},
			new Appx() {{
				name = "FUEL";
				appid = "12800";
			}},
			new Appx() {{
				name = "FEZ";
				appid = "224760";
			}},
			new Appx() {{
				name = "The Path";
				appid = "27000";
			}}
			);
		/* @formatter:on */
	}

}
