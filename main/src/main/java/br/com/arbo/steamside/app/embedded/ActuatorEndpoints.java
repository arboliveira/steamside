package br.com.arbo.steamside.app.embedded;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheContent;

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

	public static class AppinfoVdfEndpoint extends AbstractEndpoint<String>
	{

		@Override
		public String invoke()
		{
			return new DumpAppCacheContent().dumpToString();
		}

		public AppinfoVdfEndpoint()
		{
			super("appinfo_vdf");
		}
	}

}
