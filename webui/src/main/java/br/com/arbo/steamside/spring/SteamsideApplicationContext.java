package br.com.arbo.steamside.spring;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

public class SteamsideApplicationContext
		extends AnnotationConfigWebApplicationContext {

	private final MutablePicoContainerX container;

	public SteamsideApplicationContext(final MutablePicoContainerX container) {
		this.container = container;
		this.register(WebConfig.class);
	}

	public MutablePicoContainerX getContainer() {
		return container;
	}
}