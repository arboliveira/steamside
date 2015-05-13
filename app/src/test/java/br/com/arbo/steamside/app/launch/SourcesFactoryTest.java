package br.com.arbo.steamside.app.launch;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.org.springframework.boot.builder.SpringApplicationBuilderUtil;
import br.com.arbo.steamside.api.Api;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.api.exit.ExitController;
import br.com.arbo.steamside.cloud.CloudSettings;
import br.com.arbo.steamside.exit.Exit;

public class SourcesFactoryTest {

	@SuppressWarnings("static-method")
	@Test
	public void instantiation__wiringShouldBeComplete()
	{
		Sources sources = new SourcesFactory(
			new FromJava(),
			Mockito.mock(Exit.class))
				.newInstance()
				.sources(Api.class)
				.replaceWithSingleton(
					CloudSettings.class, Mockito.mock(CloudSettings.class));

		SpringApplication app =
			SpringApplicationBuilderUtil.build(
				new SpringApplicationBuilder().web(false),
				sources);

		try (ConfigurableApplicationContext context = app.run())
		{
			context.getBean(RunGameCommand.class);
			context.getBean(ExitController.class);
		}
	}

}
