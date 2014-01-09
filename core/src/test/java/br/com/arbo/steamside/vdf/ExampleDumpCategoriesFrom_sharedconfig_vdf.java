package br.com.arbo.steamside.vdf;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.Category;

public class ExampleDumpCategoriesFrom_sharedconfig_vdf {

	private static final SteamDirectory steamDirectory = new SteamDirectory(
			new FromSystemUtils());

	public static void main(final String[] args) {
		new ExampleDumpCategoriesFrom_sharedconfig_vdf().execute();
	}

	private void execute() {
		final Apps apps = new Factory_sharedconfig_vdf(
				new File_sharedconfig_vdf(new Dir_userid(new Dir_userdata(
						steamDirectory))))
				.data().apps();
		apps.accept(new CategoryWithAppsVisitor() {

			@Override
			public void visit(final Category category, final AppsHome itsApps) {
				printCategory(category, itsApps);
			}
		});
	}

	void printCategory(final Category category, final AppsHome itsApps) {
		System.out.println(indent.on(category));
		indent.increase();
		itsApps.accept(new AppsHome.Visitor() {

			@Override
			public void visit(final App app) {
				System.out.println(indent.on(dump.toInfo(app.appid())));
			}
		});
		indent.decrease();
	}

	final Indent indent = new Indent();
	final AppNameFactory appnameFactory =
			new AppNameFromLocalFiles(
					new InMemory_appinfo_vdf(new File_appinfo_vdf(
							steamDirectory)));
	final SysoutAppInfoLine dump =
			new SysoutAppInfoLine(appnameFactory);

}
