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
import br.com.arbo.steamside.steam.client.localfiles.localconfig.KV_appticket;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Entry_app;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.LastPlayed;

class Combine {

	private static void executable(final AppImpl.Builder b, AppInfo appInfo)
	{
		final String executable;
		try {
			executable = appInfo.executable();
		}
		catch (NotAvailableOnThisPlatform ex) {
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

	void eachAppticket(KV_appticket each)
	{
		@NonNull
		AppId appid = each.appid();

		final AppImpl.Builder b =
				new AppImpl.Builder()
						.appid(appid.appid());

		from_localconfig_apps(appid, b);

		from_appinfo(appid, b);

		from_sharedconfig(appid, b);

		home.add(b.make());
	}

	AppsHome reduce()
	{
		d_localconfig.apptickets().forEach(this::eachAppticket);

		return home;
	}

	private void from_appinfo(AppId appid, final AppImpl.Builder b)
	{
		AppInfo appInfo;
		try {
			appInfo = d_appinfo.get(appid);
		}
		catch (MissingFrom_appinfo_vdf e) {
			b.missingFrom_appinfo_vdf(e);
			return;
		}
		b.name(appInfo.name());
		executable(b, appInfo);
	}

	private void from_localconfig_apps(AppId appid, final AppImpl.Builder b)
	{
		@Nullable
		KV_app applocal = d_localconfig.apps().get(appid);

		if (applocal == null) return;

		@Nullable
		final LastPlayed lastPlayed = applocal.lastPlayed();
		if (lastPlayed != null)
			b.lastPlayed(lastPlayed.value);
	}

	private void from_sharedconfig(AppId appid, final AppImpl.Builder b)
	{
		@Nullable
		Entry_app each = d_sharedconfig.get(appid);

		if (each == null) return;

		each.accept(tag -> b.addCategory(tag));
	}

	private final Data_appinfo_vdf d_appinfo;

	private final Data_localconfig_vdf d_localconfig;

	private final Data_sharedconfig_vdf d_sharedconfig;

	private final InMemoryAppsHome home = new InMemoryAppsHome();

}
