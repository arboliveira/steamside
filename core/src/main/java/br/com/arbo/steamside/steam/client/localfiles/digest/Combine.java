package br.com.arbo.steamside.steam.client.localfiles.digest;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.apps.AppImpl;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.InMemoryAppsHome;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.KV_app;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Entry_app;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Entry_app.TagVisitor;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.LastPlayed;

class Combine {

	private static void executable(final AppImpl.Builder b, AppInfo appInfo) {
		final String executable;
		try {
			executable = appInfo.executable();
		} catch (NotAvailableOnThisPlatform ex) {
			b.notAvailableOnThisPlatform(ex);
			return;
		}
		b.executable(executable);
	}

	Combine(
			Data_appinfo_vdf d_appinfo,
			Data_localconfig_vdf d_localconfig,
			Data_sharedconfig_vdf d_sharedconfig) {
		this.d_appinfo = d_appinfo;
		this.d_localconfig = d_localconfig;
		this.d_sharedconfig = d_sharedconfig;
	}

	void eachApp(Entry_app each) {
		@NonNull
		AppId appid = each.appid();

		@Nullable
		KV_app applocal = d_localconfig.get(appid);

		final AppImpl.Builder b =
				new AppImpl.Builder()
						.appid(appid.appid());

		if (applocal != null) {
			@Nullable
			final LastPlayed lastPlayed = applocal.lastPlayed();
			if (lastPlayed != null)
				b.lastPlayed(lastPlayed.value);
		}

		from_appinfo(appid, b);

		each.accept(new TagVisitor() {

			@Override
			public void each(String tag) {
				b.addCategory(tag);
			}
		});

		home.add(b.make());
	}

	AppsHome reduce() {
		d_sharedconfig.apps().forEachEntry_app(this::eachApp);

		return home;
	}

	private void from_appinfo(AppId appid, final AppImpl.Builder b) {
		AppInfo appInfo;
		try {
			appInfo = d_appinfo.get(appid);
		} catch (MissingFrom_appinfo_vdf e) {
			b.missingFrom_appinfo_vdf(e);
			return;
		}
		b.name(appInfo.name());
		executable(b, appInfo);
	}

	private final Data_appinfo_vdf d_appinfo;

	private final Data_localconfig_vdf d_localconfig;

	private final Data_sharedconfig_vdf d_sharedconfig;

	private final InMemoryAppsHome home = new InMemoryAppsHome();

}
