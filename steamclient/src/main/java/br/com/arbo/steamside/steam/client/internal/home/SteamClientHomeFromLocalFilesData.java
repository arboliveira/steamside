package br.com.arbo.steamside.steam.client.internal.home;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppImpl.Builder;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.Data_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.KV_appticket;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

class SteamClientHomeFromLocalFilesData
{

	SteamClientHome combine()
	{
		InMemorySteamClientHome home =
			new InMemorySteamClientHome();

		addToHome(home);

		return home;
	}

	private void addToHome(InMemorySteamClientHome home)
	{
		Stream<AppId> c = Stream.concat(
			Stream.concat(
				appidsFrom_localconfig(),
				appidsFrom_sharedconfig()),
			appidsFrom_appinfo());

		Stream<AppId> distinct = c
			.distinct();

		Stream<Optional<AppImpl>> map = distinct.map(this::buildFromAppId);

		map.filter(o -> o.isPresent()).map(o -> o.get()).forEach(home::add);
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
		return d_appinfo.everyAppId();
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

	private Optional<AppImpl> buildFromAppId(AppId appid)
	{
		Builder builder = new AppImpl.Builder().appid(appid.appid());

		from_localconfig_apps(appid, builder);
		from_appinfo(appid, builder);
		from_sharedconfig(appid, builder);

		return builder.make();
	}

	private void from_appinfo(AppId appid, final AppImpl.Builder b)
	{
		Optional<AppInfo> optional = d_appinfo.get(appid);

		if (optional.isPresent())
		{
			AppInfo appInfo = optional.get();
			b.name(appInfo.name());
			b.type(appInfo.type());
			executables(b, appInfo);
		}
		else
		{
			if (WHY_APPINFO)
				throw MissingFrom_appinfo_vdf.appid(appid);
		}
	}

	private static final boolean WHY_APPINFO = false;

	private void from_localconfig_apps(AppId appid, AppImpl.Builder b)
	{
		d_localconfig.apps().get(appid).ifPresent(
			kv_app -> {
				b.owned();
				kv_app.lastPlayed().ifPresent(b::lastPlayed);
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

	private static void executables(AppImpl.Builder b, AppInfo appInfo)
	{
		b.executables(appInfo.executables());
	}

	private final Data_appinfo_vdf d_appinfo;
	private final Data_localconfig_vdf d_localconfig;
	private final Data_sharedconfig_vdf d_sharedconfig;

}
