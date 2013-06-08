package br.com.arbo.steamside.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.arbo.steamside.api.collection.CollectionController;
import br.com.arbo.steamside.api.exit.ExitController;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {
		CollectionController.class, ExitController.class })
public class WebConfig {
	//
}
