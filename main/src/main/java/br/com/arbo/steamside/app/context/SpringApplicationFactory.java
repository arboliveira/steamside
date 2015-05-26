package br.com.arbo.steamside.app.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.org.springframework.boot.builder.SpringApplicationBuilderUtil;

public class SpringApplicationFactory {

	public static SpringApplication buildWith(Sources sources)
	{
		return SpringApplicationBuilderUtil.build(
			new SpringApplicationBuilder().web(false).headless(false),
			sources);
	}

}
