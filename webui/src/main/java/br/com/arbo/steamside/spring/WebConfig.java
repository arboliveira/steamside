package br.com.arbo.steamside.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.arbo.steamside.api.Api;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { Api.class })
public class WebConfig {
	//
}
