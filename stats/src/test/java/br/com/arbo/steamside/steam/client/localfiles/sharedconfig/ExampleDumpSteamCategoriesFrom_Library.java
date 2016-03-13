package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.stream.Stream;

import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppAppNameType;
import br.com.arbo.steamside.steam.client.library.Libraries;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class ExampleDumpSteamCategoriesFrom_Library
{

	public static void main(final String[] args)
	{
		new ExampleDumpSteamCategoriesFrom_Library().execute();
	}

	void execute()
	{
		library.allSteamCategories().forEach(this::printCategory);
	}

	void printCategory(final SteamCategory category)
	{
		Stream<App> apps = library.findIn(category);
		new Out(category.toString(), apps.map(this::toInfo), System.out::println).out();
	}

	private String toInfo(App app)
	{
		return SysoutAppInfoLine.toInfo(new AppAppNameType(app));
	}

	private final Library library = Libraries.fromSteamPhysicalFiles();
}
