package br.com.arbo.steamside.spring;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.steamside.exit.Exit;

public class SteamsideApplicationContext
		extends AnnotationConfigWebApplicationContext {

	private final Exit exit;

	public SteamsideApplicationContext(final Exit exit) {
		this.exit = exit;
		this.register(WebConfig.class);
	}

	public Exit getExit() {
		return exit;
	}
}