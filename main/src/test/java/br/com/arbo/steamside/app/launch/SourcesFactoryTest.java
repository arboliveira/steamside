package br.com.arbo.steamside.app.launch;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.org.springframework.boot.builder.SpringApplicationBuilderUtil;
import br.com.arbo.steamside.api.Api;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.api.exit.ExitController;
import br.com.arbo.steamside.cloud.CloudSettingsFactory;

public class SourcesFactoryTest
{

	@Test
	public void instantiation__wiringShouldBeComplete()
	{
		Sources sources = new Sources();

		br.com.arbo.steamside.app.context.SourcesFactory.populate(sources);
		SourcesFactory.populate(sources);

		sources
			.sources(Api.class)
			.replaceWithSingleton(
				CloudSettingsFactory.class,
				mock(CloudSettingsFactory.class));

		SpringApplicationBuilder builder = new SpringApplicationBuilder();

		try (ConfigurableApplicationContext context =
			SpringApplicationBuilderUtil.run(
				builder.web(WebApplicationType.NONE),
				sources))
				{
			context.getBean(RunGameCommand.class);
			context.getBean(ExitController.class);
			context.getBean(User.class).username();
				}
	}

}
