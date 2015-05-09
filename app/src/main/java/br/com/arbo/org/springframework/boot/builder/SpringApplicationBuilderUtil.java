package br.com.arbo.org.springframework.boot.builder;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class SpringApplicationBuilderUtil {

	public static SpringApplicationBuilder sources(
		SpringApplicationBuilder builder, Sources sources)
	{
		return sources.apply(builder);
	}
}
