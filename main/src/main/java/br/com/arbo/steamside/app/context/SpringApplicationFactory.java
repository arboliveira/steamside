package br.com.arbo.steamside.app.context;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.org.springframework.boot.builder.SpringApplicationBuilderUtil;

public class SpringApplicationFactory
{

	public static ConfigurableApplicationContext run(Sources sources,
		String... args)
	{
		final SpringApplicationBuilder builder =
			new SpringApplicationBuilder().web(false).headless(false);
		return SpringApplicationBuilderUtil.run(
			builder, sources, args);
	}

}
