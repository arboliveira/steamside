package br.com.arbo.steamside.container;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.opersys.userhome.FromSystemUtils;
import br.com.arbo.opersys.userhome.FromWindowsUtils;
import br.com.arbo.opersys.userhome.ProgramFiles;
import br.com.arbo.opersys.userhome.UserHome;
import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.api.app.AppController;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.api.collection.CollectionController;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.cloud.Cloud;
import br.com.arbo.steamside.cloud.CloudSettings;
import br.com.arbo.steamside.cloud.CloudSettingsFromLocalSettings;
import br.com.arbo.steamside.cloud.Host;
import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.cloud.Uploader;
import br.com.arbo.steamside.cloud.autoupload.AutoUpload;
import br.com.arbo.steamside.cloud.autoupload.ParallelUpload;
import br.com.arbo.steamside.cloud.dontpad.Dontpad;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettings;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFromLocalSettings;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.continues.ContinuesFromSteamClientLocalfiles;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.continues.FilterContinues;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.autowire.Autowire;
import br.com.arbo.steamside.data.autowire.AutowireCollectionsData;
import br.com.arbo.steamside.data.autowire.AutowireKidsData;
import br.com.arbo.steamside.data.autowire.AutowireSteamsideData;
import br.com.arbo.steamside.data.autowire.AutowireTagsData;
import br.com.arbo.steamside.data.load.FromCloudAndFile;
import br.com.arbo.steamside.data.load.InitialLoad;
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

public class ContainerFactory {

	public static ContainerWeb newContainer(
			final AnnotationConfigWebApplicationContext ctx
			)
	{
		final ContainerWeb c = new ContainerWeb(ctx);
		addComponents(c);
		return c;
	}

	public static void onStart(ParallelAppsHomeFactory parallelAppsHomeFactory,
			Monitor monitor,
			br.com.arbo.steamside.data.autowire.Autowire loadData)
	{
		parallelAppsHomeFactory.start();
		monitor.start();
		loadData.start();
	}

	public static void onStop(ParallelAppsHomeFactory parallelAppsHomeFactory,
			Monitor monitor)
	{
		monitor.stop();
		parallelAppsHomeFactory.stop();
	}

	private static void addComponents(final ContainerWeb container)
	{
		container.addComponent(Settings.class, SettingsImpl.class);
		container.addComponent(
				br.com.arbo.steamside.api.app.AppSettings.class,
				br.com.arbo.steamside.api.app.AppSettingsImpl.class);

		container
				.addComponent(Library.class, LibraryImpl.class)
				.addComponent(
						AppsHomeFactory.class, ParallelAppsHomeFactory.class);

		container
				.addComponent(Monitor.class)
				.addComponent(ChangeListener.class, DigestOnChange.class);

		container
				.addComponent(
						SteamsideData.class, AutowireSteamsideData.class)
				.addComponent(
						CollectionsData.class, AutowireCollectionsData.class)
				.addComponent(
						TagsData.class, AutowireTagsData.class)
				.addComponent(
						KidsData.class, AutowireKidsData.class);

		container
				.addComponent(LoadFile.class, LoadSteamsideXml.class)
				.addComponent(File_steamside_xml.class)
				.addComponent(ParallelSave.class)
				.addComponent(InitialLoad.class, FromCloudAndFile.class)
				.addComponent(Autowire.class);

		container
				.addComponent(LoadCloud.class)
				.addComponent(Cloud.class)
				.addComponent(
						CloudSettings.class,
						CloudSettingsFromLocalSettings.class)
				.addComponent(Host.class, Dontpad.class)
				.addComponent(
						DontpadSettings.class,
						DontpadSettingsFromLocalSettings.class);

		container
				.addComponent(ParallelUpload.class)
				.addComponent(Uploader.class)
				.addComponent(SaveFile.class, AutoUpload.class);

		container
				.addComponent(File_sharedconfig_vdf.class)
				.addComponent(File_localconfig_vdf.class)
				.addComponent(File_appinfo_vdf.class)
				.addComponent(Dir_userid.class)
				.addComponent(Dir_userdata.class);

		container
				.addComponent(KidsMode.class, FromUsername.class)
				.addComponent(User.class, FromJava.class);

		container
				.addComponent(SteamBrowserProtocol.class)
				.addComponent(RunGameCommand.class);

		container
				.addComponent(FilterContinues.class)
				.addComponent(Continues.class)
				.addComponent(
						ContinuesRooster.class,
						ContinuesFromSteamClientLocalfiles.class)
				.addComponent(FavoritesOfUser.class, FromSettings.class);

		container
				.addComponent(CollectionController.class)
				.addComponent(AppController.class)
		//
		;

		registerSteamLocation(container);
	}

	private static void registerSteamLocation(final ContainerWeb container)
	{
		if (SystemUtils.IS_OS_WINDOWS)
		{
			container.addComponent(SteamLocation.class, Windows.class);
			container.addComponent(ProgramFiles.class, FromWindowsUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_LINUX)
		{
			container.addComponent(SteamLocation.class, Linux.class);
			container.addComponent(UserHome.class, FromSystemUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_MAC_OSX)
		{
			container.addComponent(SteamLocation.class, MacOSX.class);
			container.addComponent(UserHome.class, FromSystemUtils.class);
			return;
		}

		throw new UnsupportedOperationException();
	}

}
