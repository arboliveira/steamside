package br.com.arbo.steamside.app.embedded;

import java.util.Collection;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.launch.LaunchSequence;
import br.com.arbo.steamside.app.launch.LocalWebserver;
import br.com.arbo.steamside.app.launch.Running;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;
import br.com.arbo.steamside.app.launch.SourcesFactory;
import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;
import br.com.arbo.steamside.exit.Exit;

public class SpringBoot implements LocalWebserver {

	@Override
	public Running launch(int port) throws PortAlreadyInUse
	{
		Port portInUse = new Port(port);
		return LaunchSequence.launch(portInUse, exit, this::doStart);
	}

	private Running doStart(Port portInUse)
	{
		CustomizePort.portDirtyHack = portInUse.port;

		Sources sources =
			new SourcesFactory(username, exit).newInstanceLive();

		if (Objects.nonNull(sourcesCustomizers))
			sourcesCustomizers.forEach(
				customizer -> customizer.customize(sources));

		SpringApplication app = sources
			.sources(ApiServlet.class, CustomizePort.class, Welcome.class)
			.apply(new SpringApplicationBuilder())
			.build();

		app.addListeners(new BannerPrepare());

		return new EmbeddedRunning(app.run());
	}

	@Autowired(required = false)
	public Collection<SourcesCustomizer> sourcesCustomizers;
	@Inject
	public User username;
	@Inject
	public Exit exit;

}
