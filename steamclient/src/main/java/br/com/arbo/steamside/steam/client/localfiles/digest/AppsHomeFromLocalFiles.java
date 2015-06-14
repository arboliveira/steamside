package br.com.arbo.steamside.steam.client.localfiles.digest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppsHome;
import br.com.arbo.steamside.steam.client.apps.InMemoryAppsHome;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.KV_appticket;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

class AppsHomeFromLocalFiles
{

	private static void executable(final AppImpl.Builder b, AppInfo appInfo)
	{
		final String executable;
		try
		{
			executable = appInfo.executable();
		}
		catch (NotAvailableOnThisPlatform ex)
		{
			b.notAvailableOnThisPlatform(ex);
			return;
		}
		b.executable(executable);
	}

	AppsHomeFromLocalFiles(
		Data_appinfo_vdf d_appinfo,
		Data_localconfig_vdf d_localconfig,
		Data_sharedconfig_vdf d_sharedconfig)
	{
		this.d_appinfo = d_appinfo;
		this.d_localconfig = d_localconfig;
		this.d_sharedconfig = d_sharedconfig;
	}

	AppsHome combine()
	{
		Stream<AppId> appidsFrom_localconfig = appidsFrom_localconfig();
		Stream<AppId> appidsFrom_sharedconfig = appidsFrom_sharedconfig();
		Stream<AppId> appidsFrom_appinfo = appidsFrom_appinfo();

		List<Stream<AppId>> all = Arrays.asList(
			appidsFrom_localconfig, appidsFrom_sharedconfig,
			appidsFrom_appinfo);

		HashSet<AppId> uniques = new HashSet<>();

		for (Stream<AppId> appidsToGoIntoLibrary : all)
		{
			appidsToGoIntoLibrary.forEach(uniques::add);
		}

		uniques.forEach(this::eachAppId);

		return home;
	}

	void eachAppId(AppId appid)
	{
		AppImpl make = buildFromAppId(appid);
		home.add(make);
	}

	private Stream<AppId> appidsFrom_appinfo()
	{
		return d_appinfo.streamAppId();
	}

	private Stream<AppId> appidsFrom_localconfig()
	{
		return d_localconfig.apptickets()
			.all().map(KV_appticket::appid);
	}

	private Stream<AppId> appidsFrom_sharedconfig()
	{
		return d_sharedconfig.apps().streamAppId();
	}

	private AppImpl buildFromAppId(AppId appid)
	{
		AppImpl.Builder b = new AppImpl.Builder().appid(appid.appid());

		from_localconfig_apps(appid, b);
		from_appinfo(appid, b);
		from_sharedconfig(appid, b);

		final AppImpl make = b.make();
		return make;
	}

	private void from_appinfo(AppId appid, final AppImpl.Builder b)
	{
		AppInfo appInfo;
		try
		{
			appInfo = d_appinfo.get(appid);
		}
		catch (MissingFrom_appinfo_vdf e)
		{
			b.missingFrom_appinfo_vdf(e);
			return;
		}
		b.name(appInfo.name());
		b.type(appInfo.type());
		executable(b, appInfo);
	}

	private void from_localconfig_apps(AppId appid, final AppImpl.Builder b)
	{
		d_localconfig.apps().get(appid).ifPresent(
			app -> app.lastPlayed().ifPresent(
				lastPlayed -> b.lastPlayed(lastPlayed.value)));
	}

	private void from_sharedconfig(AppId appid, final AppImpl.Builder b)
	{
		d_sharedconfig.get(appid).ifPresent(
			each -> each.accept(tag -> b.addCategory(tag)));
	}

	private final Data_appinfo_vdf d_appinfo;
	private final Data_localconfig_vdf d_localconfig;
	private final Data_sharedconfig_vdf d_sharedconfig;
	private final InMemoryAppsHome home = new InMemoryAppsHome();

}
