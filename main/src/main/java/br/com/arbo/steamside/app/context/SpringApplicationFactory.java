package br.com.arbo.steamside.app.context;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.org.springframework.boot.builder.SpringApplicationBuilderUtil;

public class SpringApplicationFactory
{

	public static ConfigurableApplicationContext run(Sources sources,
		String... args)
	{
		SpringApplicationBuilder builder =
			new SpringApplicationBuilder().web(WebApplicationType.NONE).headless(false);

		return start(builder, sources, args);
	}

	private static ConfigurableApplicationContext start(
		SpringApplicationBuilder builder, Sources sources, String... args)
	{
		ConfigurableApplicationContext context =
			SpringApplicationBuilderUtil.run(builder, sources, args);

		context.start();

		return context;
	}

}
