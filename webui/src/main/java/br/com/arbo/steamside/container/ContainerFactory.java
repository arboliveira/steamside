package br.com.arbo.steamside.container;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.favorites.FromSettings;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
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
				.addComponent(SharedConfigConsume.class)
				.addComponent(CollectionFromVdf.class)
				.addComponent(Continue.class)
				.addComponent(Favorites.class)
				.addComponent(FavoritesOfUser.class, FromSettings.class)
		//
		;
	}

}
