package br.com.arbo.steamside.steam.client.library;

import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppAppNameType;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class DumpSteamCategoriesFrom_Library
{

	public void dump(Consumer<String> print)
	{
		library.allSteamCategories().forEach(
			category -> this.printCategory(category, print));
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	void printCategory(
		SteamCategory category,
		Consumer<String> print)
	{
		Stream<App> apps = library.findIn(category);
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

	@Inject
	public DumpSteamCategoriesFrom_Library(Library library)
	{
		this.library = library;
	}

	private final Library library;

}