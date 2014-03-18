package br.com.arbo.steamside.app.jetty;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.container.ContainerFactory;
import br.com.arbo.steamside.data.collections.CollectionHomeXmlFile;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.spring.WebConfig;

class WebApplicationContextBuilder {

	private final Exit exit;
	private final User username;
	private WebApplicationContextTweak tweak;

	public WebApplicationContextBuilder(
			User username,
			Exit exit,
			WebApplicationContextTweak tweak) {
		this.username = username;
		this.exit = exit;
		this.tweak = tweak;
	}

	AnnotationConfigWebApplicationContext newSpringContext() {
		final AnnotationConfigWebApplicationContext springContext =
				new AnnotationConfigWebApplicationContext();

		final ContainerWeb container =
				ContainerFactory.newContainer(springContext);

		container.addComponent(WebConfig.class);
		container.addComponent(CollectionHomeXmlFile.class);

		finishContainer(container);

		container.addComponent(AutoStartup.class);

		container.flush();

		return springContext;
	}

	private void finishContainer(final ContainerWeb cx) {
		DirtyHackForSpringWeb.UserExistingInstance.instance = this.username;
		DirtyHackForSpringWeb.ExitExistingInstance.instance = this.exit;
		cx.replaceComponent(User.class,
				DirtyHackForSpringWeb.UserExistingInstance.class);
		cx.addComponent(Exit.class,
				DirtyHackForSpringWeb.ExitExistingInstance.class);

		tweak.tweak(cx);
	}

}