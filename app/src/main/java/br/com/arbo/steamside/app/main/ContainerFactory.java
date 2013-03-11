package br.com.arbo.steamside.app.main;

import static org.picocontainer.Characteristics.CACHE;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import br.com.arbo.steamside.app.browser.LetJavaOpen;
import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.instance.DetectSteamside;
import br.com.arbo.steamside.app.instance.FromURL;
import br.com.arbo.steamside.app.instance.LimitPossiblePorts;
import br.com.arbo.steamside.app.instance.SingleInstancePerUser;
import br.com.arbo.steamside.app.jetty.Jetty;
import br.com.arbo.steamside.app.jetty.LocalWebserver;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.opersys.username.User;

public class ContainerFactory {

	public static MutablePicoContainer newContainer() {
		final MutablePicoContainer container = newContainerEmpty();
		container.setName("Launch");

		class ContainerStop implements Exit {

			@Override
			public void exit() {
				container.stop();
			}

		}

		container
				.addComponent(SingleInstancePerUser.class)
				.addComponent(DetectSteamside.class, FromURL.class)
				.addComponent(new LimitPossiblePorts(10))
				.addComponent(LocalWebserver.class, Jetty.class)
				.addComponent(WebBrowser.class, LetJavaOpen.class)
				.addComponent(KidsMode.class, FromUsername.class)
				.addComponent(User.class, FromJava.class)
				.addComponent(Exit.class, new ContainerStop())
		//
		;

		return container;
	}

	public static MutablePicoContainer newContainerEmpty() {
		final MutablePicoContainer container = new PicoBuilder()
				.withCaching()
				.withLifecycle()
				.build();
		container.change(CACHE);
		return container;
	}

}
