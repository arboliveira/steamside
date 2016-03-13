package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ActuatorEndpoints
{

	@Bean
	public static Endpoint<String> appinfo_vdf()
	{
		return new AppinfoVdfEndpoint("appinfo_vdf");
	}

	@Bean
	public static Endpoint<String> appinfo_vdf_apps()
	{
		return new AppinfoVdfAppsEndpoint("appinfo_vdf_apps");
	}

	@Bean
	public static Endpoint<String> appinfo_vdf_names()
	{
		return appInfoVdfNamesEndpoint;
	}

	@Bean
	public static MvcEndpoint appinfo_vdf_names_MVC()
	{
		return new AppInfoVdfNamesMvcEndpoint(appInfoVdfNamesEndpoint);
	}

	private static final Endpoint<String> appInfoVdfNamesEndpoint =
		new AppInfoVdfNamesEndpoint("appinfo_vdf_names");

}
