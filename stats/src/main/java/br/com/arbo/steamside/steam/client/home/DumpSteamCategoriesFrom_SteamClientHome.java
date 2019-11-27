package br.com.arbo.steamside.steam.client.home;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppNameType;
import br.com.arbo.steamside.steam.client.types.AppNameTypes;

public class DumpSteamCategoriesFrom_SteamClientHome
{

	public void dump(Consumer<String> print)
	{
		steamClientHome.categories().everySteamCategory().forEach(
			category -> this.printCategory(category, print));
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	@Inject
	public DumpSteamCategoriesFrom_SteamClientHome(
		SteamClientHome steamClientHome)
	{
		this.steamClientHome = steamClientHome;
	}

	void printCategory(
		SteamCategory category,
		Consumer<String> print)
	{
		Stream<App> apps = steamClientHome.apps_categories().findIn(category);
		new Out(
			category.toString(),
			apps.map(this::toInfo),
			print)
				.out();
	}

	private String toInfo(App app)
	{
		return SysoutAppInfoLine.toInfo(new AppAppNameType(app));
	}

	private final SteamClientHome steamClientHome;

	public static class AppAppNameType implements AppNameType
	{

		@Override
		public AppId appid()
		{
			return app.appid();
		}

		@Override
		public Optional<String> appnametype()
		{
			return Optional
				.of(AppNameTypes.appnametype(app.name(), app.type()));
		}

		public AppAppNameType(App app)
		{
			this.app = app;
		}

		private final App app;
	}

}