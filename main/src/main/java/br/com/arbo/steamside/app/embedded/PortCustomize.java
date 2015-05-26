package br.com.arbo.steamside.app.embedded;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

class PortCustomize implements EmbeddedServletContainerCustomizer {

	PortCustomize(int port)
	{
		this.port = port;
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container)
	{
		container.setPort(port);
	}

	private int port;

}