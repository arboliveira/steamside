package br.com.arbo.steamside.app.embedded;

import java.util.Collection;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.org.springframework.boot.builder.SpringApplicationBuilderUtil;
import br.com.arbo.steamside.app.launch.LaunchSequence;
import br.com.arbo.steamside.app.launch.LocalWebserver;
import br.com.arbo.steamside.app.launch.Running;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;
import br.com.arbo.steamside.app.launch.SourcesFactory;
import br.com.arbo.steamside.app.port.Port;
import br.com.arbo.steamside.app.port.PortAlreadyInUse;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;

public class SpringBoot implements LocalWebserver
{

	@SuppressWarnings("resource")
	private static Running start(
		Sources sources, SpringApplicationBuilder builder)
	{
		ConfigurableApplicationContext context =
			SpringApplicationBuilderUtil.run(builder, sources);

		context.start();

		return new EmbeddedRunning(context);
	}

	@Override
	public Running launch(int port) throws PortAlreadyInUse
	{
		return LaunchSequence.launch(new Port(port), exit, this::doStart);
	}

	private Running doStart(Port portInUse)
	{
		Sources sources = SourcesFactory.newInstance();

		if (Objects.nonNull(sourcesCustomizers))
			sourcesCustomizers.forEach(
				customizer -> customizer.customize(sources));

		SpringApplicationBuilder builder = new SpringApplicationBuilder();

		reuseFromParent(sources);

		builder.listeners(new BannerPrepare());

		sources
			.sources(ApiServlet.class, SimpleCORSFilter.class)
			.registerSingleton(new PortCustomize(portInUse.port));

		return start(sources, builder);
	}

	private void reuseFromParent(Sources sources)
	{
		sources.registerSingleton(Exit.class, exit);
		sources.registerSingleton(SteamLocation.class, steamLocation);
		sources.registerSingleton(User.class, username);
		sources.registerSingleton(dir_userid);
	}

	@Inject
	public Dir_userid dir_userid;
	@Inject
	public Exit exit;
	@Autowired(required = false)
	public Collection<SourcesCustomizer> sourcesCustomizers;
	@Inject
	public SteamLocation steamLocation;
	@Inject
	public User username;

}
