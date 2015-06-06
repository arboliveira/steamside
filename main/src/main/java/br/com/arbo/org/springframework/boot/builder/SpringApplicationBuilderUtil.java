package br.com.arbo.org.springframework.boot.builder;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringApplicationBuilderUtil
{

	public static ConfigurableApplicationContext run(
		SpringApplicationBuilder builder, Sources sources, String... args)
	{
		builder.sources(sources.sources_arg());
		builder.listeners(
			new SingletonsPrepare(sources.registerSingleton_args()));
		return builder.run(args);
	}

}
