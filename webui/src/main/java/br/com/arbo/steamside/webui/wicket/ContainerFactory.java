package br.com.arbo.steamside.webui.wicket;

import static org.picocontainer.Characteristics.CACHE;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

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
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.webui.wicket.continuejson.ContinueJson;
import br.com.arbo.steamside.webui.wicket.favorites.json.FavoritesJson;
import br.com.arbo.steamside.webui.wicket.search.SearchJson;
import br.com.arbo.steamside.webui.wicket.session.json.SessionJson;

public class ContainerFactory {

	public static MutablePicoContainer newContainer() {
		final MutablePicoContainer container = new PicoBuilder()
				.withCaching()
				.withLifecycle()
				.build();
		container.change(CACHE);

		container.setName("WicketApplication");
		container
				.addComponent(KidsMode.class, FromUsername.class)
				.addComponent(User.class, FromJava.class)
				.addComponent(SteamBrowserProtocol.class)
				.addComponent(AppNameFactory.class, AppNameFromLocalFiles.class)
				.addComponent(InMemory_appinfo_vdf.class)
				.addComponent(RunGame.class)
				.addComponent(SharedConfigConsume.class)
				.addComponent(CollectionFromVdf.class)
				.addComponent(ContinueJson.class)
				.addComponent(FavoritesJson.class)
				.addComponent(Continue.class)
				.addComponent(Favorites.class)
				.addComponent(FavoritesOfUser.class, FromSettings.class)
				.addComponent(SearchJson.class)
				.addComponent(SessionJson.class)
		//
		;

		return container;
	}

}
