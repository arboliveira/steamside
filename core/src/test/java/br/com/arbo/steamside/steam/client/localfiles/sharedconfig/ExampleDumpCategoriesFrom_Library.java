package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsCollection;
import br.com.arbo.steamside.apps.AppsHome.CategoryWithAppsVisitor;
import br.com.arbo.steamside.indent.Indent;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.Category;

public class ExampleDumpCategoriesFrom_Library {

	public static void main(final String[] args) {
		new ExampleDumpCategoriesFrom_Library().execute();
	}

	private void execute() {
		Library library = Library_ForExamples.fromSteamPhysicalFiles();

		library.accept(new CategoryWithAppsVisitor() {

			@Override
			public void visit(final Category category,
					final AppsCollection itsApps) {
				printCategory(category, itsApps);
			}
		});

	}

	void printCategory(final Category category, final AppsCollection itsApps) {
		System.out.println(indent.on(category));
		indent.increase();
		itsApps.accept(new App.Visitor() {

			@Override
			public void each(final App app) {
				System.out.println(indent.on(dump.toInfo(app.appid())));
			}
		});
		indent.decrease();
	}

	final Indent indent = new Indent();
	final AppNameFactory appnameFactory =
			new AppNameFromLocalFiles(
					new InMemory_appinfo_vdf(new File_appinfo_vdf(
							SteamDirectory_ForExamples
									.fromSteamPhysicalFiles())));
	final SysoutAppInfoLine dump =
			new SysoutAppInfoLine(appnameFactory);

}
