package br.com.arbo.steamside.container;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.org.apache.commons.lang3.FromWindowsUtils;
import br.com.arbo.org.apache.commons.lang3.ProgramFiles;
import br.com.arbo.org.apache.commons.lang3.UserHome;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.continues.ContinuesFromSteamClientLocalfiles;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.continues.FilterContinues;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.favorites.FromSettings;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.library.LibraryImpl;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.settings.file.File_steamside_xml;
import br.com.arbo.steamside.settings.file.Load;
import br.com.arbo.steamside.settings.file.Save;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.AutoreloadingAppsHomeFactory;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.ChangeListener;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.DigestOnChange;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Digester;
import br.com.arbo.steamside.steam.client.localfiles.monitoring.Monitor;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Linux;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.MacOSX;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Windows;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;

public class ContainerFactory {

	public static ContainerWeb newContainer(
			final AnnotationConfigWebApplicationContext ctx
			) {
		final ContainerWeb c = new ContainerWeb(ctx);
		addComponents(c);
		return c;
	}

	private static void addComponents(final ContainerWeb container) {
		container
				.addComponent(Library.class, LibraryImpl.class)
				.addComponent(AppsHomeFactory.class, AutoreloadingAppsHomeFactory.class)
				.addComponent(KidsMode.class, FromUsername.class)
				.addComponent(User.class, FromJava.class)
				.addComponent(SteamBrowserProtocol.class)
				//				.addComponent(AppNameFactory.class, AppNameFromLocalFiles.class)
				.addComponent(
						Data_appinfo_vdf.class,
						InMemory_appinfo_vdf.class)
				.addComponent(RunGame.class)
				//				.addComponent(
				//						DataFactory_sharedconfig_vdf.class,
				//						AutoreloadingDataFactory_sharedconfig_vdf.class)
				.addComponent(File_sharedconfig_vdf.class)
				.addComponent(File_localconfig_vdf.class)
				.addComponent(File_appinfo_vdf.class)
				.addComponent(Dir_userid.class)
				.addComponent(Dir_userdata.class)
				.addComponent(Load.class)
				.addComponent(File_steamside_xml.class)
				.addComponent(Save.class)
				.addComponent(FilterContinues.class)
				.addComponent(Continues.class)
				.addComponent(
						ContinuesRooster.class,
						ContinuesFromSteamClientLocalfiles.class)
				.addComponent(Favorites.class)
				.addComponent(FavoritesOfUser.class, FromSettings.class)
				.addComponent(Digester.class)
				.addComponent(Monitor.class)
				.addComponent(ChangeListener.class, DigestOnChange.class)
		//
		;

		registerSteamLocation(container);
	}

	private static void registerSteamLocation(final ContainerWeb container) {
		if (SystemUtils.IS_OS_WINDOWS) {
			container.addComponent(SteamLocation.class, Windows.class);
			container.addComponent(ProgramFiles.class, FromWindowsUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_LINUX) {
			container.addComponent(SteamLocation.class, Linux.class);
			container.addComponent(UserHome.class, FromSystemUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_MAC_OSX) {
			container.addComponent(SteamLocation.class, MacOSX.class);
			container.addComponent(UserHome.class, FromSystemUtils.class);
			return;
		}

		throw new UnsupportedOperationException();
	}

}
