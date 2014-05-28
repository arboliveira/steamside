package br.com.arbo.steamside.app.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.app.browser.LetJavaOpen;
import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.injection.Container;
import br.com.arbo.steamside.app.instance.DetectSteamside;
import br.com.arbo.steamside.app.instance.FromURL;
import br.com.arbo.steamside.app.instance.LimitPossiblePorts;
import br.com.arbo.steamside.app.instance.SingleInstancePerUser;
import br.com.arbo.steamside.app.jetty.Jetty;
import br.com.arbo.steamside.app.jetty.LocalWebserver;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;
import br.com.arbo.steamside.exit.Exit;

public class ContainerFactory {

	@SuppressWarnings("resource")
	public static Container newContainer() {
		final AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext();
		final Container container = new Container(ctx);

		container
				.addComponent(SingleInstancePerUser.class)
				.addComponent(DetectSteamside.class, FromURL.class)
				.addComponent(User.class, FromJava.class)
				.addComponent(new LimitPossiblePorts(10))
				.addComponent(LocalWebserver.class, Jetty.class)
				.addComponent(WebBrowser.class, LetJavaOpen.class)
				.addComponent(WebApplicationContextTweak.class, NoTweak.class);

		final ContainerStop instance = new ContainerStop(container);
		container.addComponent(Exit.class, instance);

		return container;
	}

}
