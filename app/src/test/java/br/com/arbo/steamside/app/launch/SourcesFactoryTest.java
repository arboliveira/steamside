package br.com.arbo.steamside.app.launch;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
				.replaceWithConfiguration(
					CloudSettings.class, MockCloudSettings.class);

		SpringApplicationBuilder builder = SpringApplicationBuilderUtil.sources(
			new SpringApplicationBuilder(), sources)
			.web(false);

		SpringApplication app = builder.build();

		try (ConfigurableApplicationContext context = app.run())
		{
			context.getBean(RunGameCommand.class);
			context.getBean(ExitController.class);
		}
	}

	@Configuration
	public static class MockCloudSettings {

		@Bean
		public static CloudSettings mockCloudSettings()
		{
			return Mockito.mock(CloudSettings.class);
		}

	}

}
