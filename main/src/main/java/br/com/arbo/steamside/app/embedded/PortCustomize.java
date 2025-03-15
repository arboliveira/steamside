package br.com.arbo.steamside.app.embedded;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

class PortCustomize implements
		WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	PortCustomize(int port)
	{
		this.port = port;
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory container)
	{
		container.setPort(port);
	}

	private int port;

}