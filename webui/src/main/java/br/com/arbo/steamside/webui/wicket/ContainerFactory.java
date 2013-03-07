package br.com.arbo.steamside.webui.wicket;

import static org.picocontainer.Characteristics.CACHE;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.opersys.username.Username;
import br.com.arbo.steamside.steam.client.executable.KillSteamIfAlreadyRunningInADifferentUserSession;
import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.webui.wicket.continuejson.ContinueJson;
import br.com.arbo.steamside.webui.wicket.search.SearchJson;

public class ContainerFactory {

	static MutablePicoContainer newContainer(final Username username) {
		final MutablePicoContainer container = new PicoBuilder()
				.withCaching()
				.withLifecycle()
				.build();

		container.setName("WicketApplication");
		container.change(CACHE);
		container
				.addComponent(Username.class, username)
				.addComponent(
						KillSteamIfAlreadyRunningInADifferentUserSession.class)
				.addComponent(SteamBrowserProtocol.class)
				.addComponent(AppNameFactory.class, AppNameFromLocalFiles.class)
				.addComponent(
						Content_appinfo_vdf.class,
						InMemory_appinfo_vdf.class)
				.addComponent(SharedConfigConsume.class)
				.addComponent(ContinueJson.class)
				.addComponent(
						Continue.Needs.class,
						ContinueNeedsImpl.class)
				.addComponent(SearchJson.class)
		//
		;

		return container;
	}

}
