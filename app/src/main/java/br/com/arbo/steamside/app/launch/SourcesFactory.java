package br.com.arbo.steamside.app.launch;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.opersys.userhome.FromSystemUtils;
import br.com.arbo.opersys.userhome.FromWindowsUtils;
import br.com.arbo.opersys.userhome.ProgramFiles;
import br.com.arbo.opersys.userhome.UserHome;
import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.cloud.Cloud;
import br.com.arbo.steamside.cloud.CloudSettingsFactory;
import br.com.arbo.steamside.cloud.CloudSettingsFromLocalSettings;
import br.com.arbo.steamside.cloud.Host;
import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.cloud.Uploader;
import br.com.arbo.steamside.cloud.autoupload.AutoUpload;
import br.com.arbo.steamside.cloud.autoupload.ParallelUpload;
import br.com.arbo.steamside.cloud.dontpad.Dontpad;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFactory;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFromLocalSettings;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.container.ParallelAppsHomeFactory;
import br.com.arbo.steamside.continues.ContinuesFromSteamClientLocalfiles;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.autowire.Autowire;
import br.com.arbo.steamside.data.autowire.AutowireCollectionsData;
import br.com.arbo.steamside.data.autowire.AutowireKidsData;
import br.com.arbo.steamside.data.autowire.AutowireSteamsideData;
import br.com.arbo.steamside.data.autowire.AutowireTagsData;
import br.com.arbo.steamside.data.load.FromCloudAndFile;
import br.com.arbo.steamside.data.load.InitialLoad;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.favorites.FromSettings;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.settings.SettingsImpl;
import br.com.arbo.steamside.settings.file.File_steamside_xml;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.settings.local.File_steamside_local_xml;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsLoad;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.library.LibraryImpl;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.ChangeListener;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.DigestOnChange;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Linux;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.MacOSX;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Windows;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.xml.autosave.ParallelSave;

public class SourcesFactory {

	private static Sources addComponents(Sources container)
	{
		container
			.sources(AutoStartup.class)
			.sourceImplementor(Settings.class, SettingsImpl.class)
			.sourceImplementor(
				br.com.arbo.steamside.api.app.AppSettings.class,
				br.com.arbo.steamside.api.app.AppSettingsImpl.class);

		container
			.sourceImplementor(Library.class, LibraryImpl.class)
			.sourceImplementor(
				AppsHomeFactory.class, ParallelAppsHomeFactory.class);

		container
			.sources(Monitor.class)
			.sourceImplementor(ChangeListener.class, DigestOnChange.class);

		container
			.sourceImplementor(
				SteamsideData.class, AutowireSteamsideData.class)
			.sourceImplementor(
				CollectionsData.class, AutowireCollectionsData.class)
			.sourceImplementor(
				TagsData.class, AutowireTagsData.class)
			.sourceImplementor(
				KidsData.class, AutowireKidsData.class);

		container
			.sourceImplementor(LoadFile.class, LoadSteamsideXml.class)
			.sourceImplementor(InitialLoad.class, FromCloudAndFile.class)
			.sources(
				File_steamside_xml.class,
				ParallelSave.class,
				Autowire.class);

		container
			.sourceImplementor(
				LocalSettingsFactory.class,
				LocalSettingsLoad.class)
			.sources(
				File_steamside_local_xml.class,
				LoadCloud.class,
				Cloud.class)
			.sourceImplementor(
				CloudSettingsFactory.class,
				CloudSettingsFromLocalSettings.class)
			.sourceImplementor(Host.class, Dontpad.class)
			.sourceImplementor(
				DontpadSettingsFactory.class,
				DontpadSettingsFromLocalSettings.class);

		container
			.sources(
				ParallelUpload.class,
				Uploader.class)
			.sourceImplementor(SaveFile.class, AutoUpload.class);

		container
			.sources(
				File_sharedconfig_vdf.class,
				File_localconfig_vdf.class,
				File_appinfo_vdf.class,
				Dir_userid.class,
				Dir_userdata.class);

		container
			.sourceImplementor(KidsMode.class, FromUsername.class);

		container
			.sources(
				SteamBrowserProtocol.class,
				RunGameCommand.class);

		container
			.sources(Continues.class)
			.sourceImplementor(
				ContinuesRooster.class,
				ContinuesFromSteamClientLocalfiles.class)
			.sourceImplementor(FavoritesOfUser.class, FromSettings.class);

		registerSteamLocation(container);

		return container;
	}

	private static void registerSteamLocation(Sources container)
	{
		if (SystemUtils.IS_OS_WINDOWS)
		{
			container
				.sourceImplementor(SteamLocation.class, Windows.class)
				.sourceImplementor(ProgramFiles.class, FromWindowsUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_LINUX)
		{
			container
				.sourceImplementor(SteamLocation.class, Linux.class)
				.sourceImplementor(UserHome.class, FromSystemUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_MAC_OSX)
		{
			container
				.sourceImplementor(SteamLocation.class, MacOSX.class)
				.sourceImplementor(UserHome.class, FromSystemUtils.class);
			return;
		}

		throw new UnsupportedOperationException();
	}

	public SourcesFactory(
		User username,
		Exit exit)
	{
		this.username = username;
		this.exit = exit;
	}

	public Sources newInstanceLive()
	{
		return newInstance().sources(StartStop.class);
	}

	Sources newInstance()
	{
		return finish(addComponents(new Sources()));
	}

	private Sources finish(Sources s)
	{
		return s
			.registerSingleton(User.class, this.username)
			.registerSingleton(Exit.class, this.exit);
	}

	private final Exit exit;
	private final User username;

}