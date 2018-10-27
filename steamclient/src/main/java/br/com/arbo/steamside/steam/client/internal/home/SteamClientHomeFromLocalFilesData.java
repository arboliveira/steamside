package br.com.arbo.steamside.steam.client.internal.home;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppImpl.Builder;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.KV_appticket;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

class SteamClientHomeFromLocalFilesData
{

	SteamClientHome combine()
	{
		InMemorySteamClientHome home =
			new InMemorySteamClientHome();

		Stream.concat(
			Stream.concat(appidsFrom_localconfig(), appidsFrom_sharedconfig()),
			appidsFrom_appinfo())
			.distinct().map(this::buildFromAppId)
			.forEach(home::add);

		return home;
	}

	SteamClientHomeFromLocalFilesData(
		Data_appinfo_vdf d_appinfo,
		Data_localconfig_vdf d_localconfig,
		Data_sharedconfig_vdf d_sharedconfig)
	{
		this.d_appinfo = d_appinfo;
		this.d_localconfig = d_localconfig;
		this.d_sharedconfig = d_sharedconfig;
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
		return d_sharedconfig.everyAppId();
	}

	private AppImpl buildFromAppId(AppId appid)
	{
		Builder builder = new AppImpl.Builder().appid(appid.appid());

		from_localconfig_apps(appid, builder);
		from_appinfo(appid, builder);
		from_sharedconfig(appid, builder);

		return builder.make();
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

	private void from_localconfig_apps(AppId appid, AppImpl.Builder b)
	{
		d_localconfig.apps().get(appid).ifPresent(
			kv_app -> {
				b.owned();
				kv_app.lastPlayed()
					.map(LastPlayed::value)
					.ifPresent(
						b::lastPlayed);
			});
	}

	private void from_sharedconfig(AppId appid, AppImpl.Builder b)
	{
		d_sharedconfig.get(appid).ifPresent(
			entry_app -> {
				b.owned();
				entry_app.accept(b::addCategory);
			});
	}

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

	private final Data_appinfo_vdf d_appinfo;
	private final Data_localconfig_vdf d_localconfig;
	private final Data_sharedconfig_vdf d_sharedconfig;

}
