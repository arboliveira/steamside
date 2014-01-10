package br.com.arbo.steamside.app.jetty;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.container.ContainerFactory;
import br.com.arbo.steamside.data.collections.CollectionHomeXmlFile;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.spring.WebConfig;

class WebApplicationContextBuilder {

	private final Exit exit;
	private final User username;
	private final KidsMode kidsmode;

	public WebApplicationContextBuilder(final User username,
			final KidsMode kidsmode, final Exit exit) {
		this.username = username;
		this.kidsmode = kidsmode;
		this.exit = exit;
	}

	AnnotationConfigWebApplicationContext newSpringContext() {
		final AnnotationConfigWebApplicationContext springContext =
				new AnnotationConfigWebApplicationContext();
		final ContainerWeb container =
				ContainerFactory.newContainer(springContext);
		container.addComponent(WebConfig.class);
		container.addComponent(CollectionHomeXmlFile.class);
		finishContainer(container);
		return springContext;
	}

	private void finishContainer(final ContainerWeb cx) {
		DirtyHackForSpringWeb.KidsModeExistingInstance.instance = this.kidsmode;
		DirtyHackForSpringWeb.UserExistingInstance.instance = this.username;
		DirtyHackForSpringWeb.ExitExistingInstance.instance = this.exit;
		cx.replaceComponent(KidsMode.class,
				DirtyHackForSpringWeb.KidsModeExistingInstance.class);
		cx.replaceComponent(User.class,
				DirtyHackForSpringWeb.UserExistingInstance.class);
		cx.addComponent(Exit.class,
				DirtyHackForSpringWeb.ExitExistingInstance.class);
		cx.flush();
	}

}