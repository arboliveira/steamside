package br.com.arbo.steamside.demo.main;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.apps.InMemoryAppsHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

class DemoAppsHome {

	static void customize(Sources sources)
	{
		sources.replaceWithSingleton(
			AppsHomeFactory.class, demoAppsHomeFactory());
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

	private static App appPapersPlease()
	{
		return app(new Appx() {

			{
				name = "Papers, Please";
				appid = "239030";
				lastPlayed = 1;
			}
		});
	}

	private static App appWindosill()
	{
		return app(new Appx() {

			{
				name = "Windosill";
				appid = "37600";
				lastPlayed = 2;
			}
		});
	}

	private static AppsHomeFactory demoAppsHomeFactory()
	{
		AppsHome appsHome = newInstance();
		return () -> appsHome;
	}

	private static AppsHome newInstance()
	{
		InMemoryAppsHome appsHome = new InMemoryAppsHome();
		populate(appsHome);
		return appsHome;
	}

	private static void populate(InMemoryAppsHome appsHome)
	{
		appsHome.add(appWindosill());
		appsHome.add(appPapersPlease());
	}

	static class Appx {

		String appid;
		long lastPlayed;
		String name;

	}

}
