package br.com.arbo.steamside.html.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.arbo.steamside.api.collection.CollectionController;

@Configuration
@EnableWebMvc
@ComponentScan(
		basePackageClasses =
		{ CollectionController.class
		// ,	com.codetutr.service.PersonService.class 
		})
public class WebConfig {
	//
}
