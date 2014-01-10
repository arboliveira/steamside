package br.com.arbo.steamside.container;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.org.apache.commons.lang3.FromSystemUtils;
import br.com.arbo.org.apache.commons.lang3.UserHome;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.FilterContinues;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.favorites.FromSettings;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.settings.file.File_steamside_xml;
import br.com.arbo.steamside.settings.file.Load;
import br.com.arbo.steamside.settings.file.Save;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DataFactory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.store.AppNameFactory;

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
				.addComponent(KidsMode.class, FromUsername.class)
				.addComponent(User.class, FromJava.class)
				.addComponent(SteamBrowserProtocol.class)
				.addComponent(AppNameFactory.class, AppNameFromLocalFiles.class)
				.addComponent(InMemory_appinfo_vdf.class)
				.addComponent(RunGame.class)
				.addComponent(DataFactory_sharedconfig_vdf.class,
						AutoreloadingDataFactory_sharedconfig_vdf.class)
				.addComponent(CollectionFromVdf.class)
				.addComponent(File_sharedconfig_vdf.class)
				.addComponent(File_appinfo_vdf.class)
				.addComponent(SteamDirectory.class)
				.addComponent(UserHome.class, FromSystemUtils.class)
				.addComponent(Dir_userid.class)
				.addComponent(Dir_userdata.class)
				.addComponent(Load.class)
				.addComponent(File_steamside_xml.class)
				.addComponent(Save.class)
				.addComponent(FilterContinues.class)
				.addComponent(Continues.class)
				.addComponent(Favorites.class)
				.addComponent(FavoritesOfUser.class, FromSettings.class)
		//
		;
	}

}
