package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.actuate.endpoint.Endpoint;
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
		return new AppinfoVdfEndpoint();
	}

	@Bean
	public static Endpoint<String> appinfo_vdf_apps()
	{
		return new AppinfoVdfAppsEndpoint();
	}

}
