package br.com.arbo.org.springframework.boot.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class SpringApplicationBuilderUtil {

	public static SpringApplication build(
		SpringApplicationBuilder builder, Sources sources)
	{
		builder.sources(sources.sources_arg());
		SpringApplication application = builder.build();
		application
			.addListeners(
				new SingletonsPrepare(sources.registerSingleton_args()));
		return application;
	}
}
