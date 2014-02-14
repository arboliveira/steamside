package br.com.arbo.steamside.continues;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.Library_ForExamples;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.AppId;

public class ExampleDumpContinues {

	public static void main(final String[] args) {
		final InMemory_appinfo_vdf appinfo =
				new InMemory_appinfo_vdf(new File_appinfo_vdf(
						SteamDirectory_ForExamples.fromSteamPhysicalFiles()));
		final FilterContinues continues =
				new FilterContinues(appinfo, new FromUsername(new FromJava()));
		final Library library = Library_ForExamples.fromSteamPhysicalFiles();

		new ContinuesFromSteamClientLocalfiles(continues, library)
				.accept(/* @formatter:off */new AppVisitor() { 
					@Override
					public void each /* @formatter:on */
							(final App app) {
						final AppId appid = app.appid();
						final AppInfo info = appinfo.get(appid);
						System.out.println(
								app.lastPlayed()
										+ " :: " + info.name()
										+ " :: " + appid);
					}
				});
	}
}
