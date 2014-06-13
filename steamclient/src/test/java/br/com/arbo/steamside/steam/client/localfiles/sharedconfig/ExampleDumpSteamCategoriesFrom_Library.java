package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.stream.Stream;

import br.com.arbo.steamside.indent.Indent;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppAppNameType;
import br.com.arbo.steamside.steam.client.library.Libraries;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.types.AppNameType;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class ExampleDumpSteamCategoriesFrom_Library {

	public static void main(final String[] args)
	{
		new ExampleDumpSteamCategoriesFrom_Library().execute();
	}

	private static String info(App app)
	{
		final AppNameType dump = new AppAppNameType(app);

		return SysoutAppInfoLine.toInfo(dump);
	}

	void execute()
	{
		library.allSteamCategories().forEach(this::printCategory);
	}

	void printApp(App app)
	{
		String info = info(app);
		System.out.println(indent.on(info));
	}

	void printCategory(final SteamCategory category)
	{
		System.out.println(indent.on(category));
		indent.increase();
		Stream<App> apps = library.findIn(category);
		apps.forEach(this::printApp);
		indent.decrease();
	}

	final Data_appinfo_vdf appinfo =
			new InMemory_appinfo_vdf(new File_appinfo_vdf(
					SteamLocations
							.fromSteamPhysicalFiles()));

	final Indent indent = new Indent();

	Library library = Libraries.fromSteamPhysicalFiles();
}
