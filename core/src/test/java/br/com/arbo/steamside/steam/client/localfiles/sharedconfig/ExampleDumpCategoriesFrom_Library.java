package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.util.stream.Stream;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.indent.Indent;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.types.Category;

public class ExampleDumpCategoriesFrom_Library {

	public static void main(final String[] args) {
		new ExampleDumpCategoriesFrom_Library().execute();
	}

	final Data_appinfo_vdf appinfo =
			new InMemory_appinfo_vdf(new File_appinfo_vdf(
					SteamDirectory_ForExamples
							.fromSteamPhysicalFiles()));

	final SysoutAppInfoLine dump =
			new SysoutAppInfoLine(appinfo);

	final Indent indent = new Indent();

	Library library = Library_ForExamples.fromSteamPhysicalFiles();

	void execute() {
		library.allSteamCategories().forEach(this::printCategory);
	}

	void printApp(App app) {
		System.out.println(indent.on(dump.toInfo(app.appid())));
	}

	void printCategory(final Category category) {
		System.out.println(indent.on(category));
		indent.increase();
		Stream<App> apps = library.findIn(category);
		apps.forEach(this::printApp);
		indent.decrease();
	}
}
