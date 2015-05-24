package br.com.arbo.steamside.demo.main;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.apps.InMemoryAppsHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

class DemoAppsHome {

	static AppsHomeFactory demoAppsHomeFactory()
	{
		AppsHome appsHome = newInstance();
		return () -> appsHome;
	}

	private static void add(InMemoryAppsHome appsHome, Appx... appxs)
	{
		for (Appx appx : appxs)
		{
			appsHome.add(app(appx));
		}
	}

	private static App app(Appx appx)
	{
		return new AppImpl.Builder()
			.name(new AppName(appx.name))
			.appid(appx.appid)
			.type(AppType.GAME)
			.lastPlayed(String.valueOf(appx.lastPlayed))
			.executable("!" + NotAvailableOnThisPlatform.class.getSimpleName())
			.make();
	}

	private static AppsHome newInstance()
	{
		InMemoryAppsHome appsHome = new InMemoryAppsHome();
		populate(appsHome);
		return appsHome;
	}

	private static void populate(InMemoryAppsHome appsHome)
	{
		/* @formatter:off */
		add(appsHome,
			new Appx() {{
				name = "Half-Life";
				appid = "70";
			}},
			new Appx() {{
				name = "Portal";
				appid = "400";
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

	static class Appx {

		Appx()
		{
			lastPlayed = i--;
		}

		String appid;
		long lastPlayed;
		String name;

		static long i = 1000;

	}

}
