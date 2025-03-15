package br.com.arbo.steamside.app.embedded;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import br.com.arbo.steamside.api.ApiWebMVC;

@EnableAutoConfiguration
@Configuration
public class ApiServlet {

	@Bean
	public ServletRegistrationBean api()
	{
		String prefix = br.com.arbo.steamside.mapping.Api.api;
		ServletRegistrationBean srb =
			new ServletRegistrationBean(
				newDispatcherServlet(), "/" + prefix + "/*");
		srb.setName(prefix);
		return srb;
	}

	private AnnotationConfigWebApplicationContext newApplicationContext()
	{
		AnnotationConfigWebApplicationContext a =
			new AnnotationConfigWebApplicationContext();
		a.setParent(parent);
		a.register(ApiWebMVC.class);
		return a;
	}

	private DispatcherServlet newDispatcherServlet()
	{
		return new DispatcherServlet(newApplicationContext());
	}

	@Inject
	public ApplicationContext parent;

}
