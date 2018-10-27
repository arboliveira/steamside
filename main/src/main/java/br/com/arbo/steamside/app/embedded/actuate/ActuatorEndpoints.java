package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.steam.client.home.DumpSteamCategoriesFrom_SteamClientHome;
import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheContent;
import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheParse;
import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppNamesGivenIds;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpAppNamesFrom_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.DumpVdfStructureFrom_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpAppNamesFrom_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DumpVdfStructureFrom_sharedconfig_vdf;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ActuatorEndpoints
{

	public static void contribute(Sources sources)
	{
		sources.sources(
			DumpAppCacheParse.class,
			DumpAppCacheContent.class,
			DumpAppNamesGivenIds.class,
			DumpAppNamesFrom_localconfig_vdf.class,
			DumpAppNamesFrom_sharedconfig_vdf.class,
			DumpSteamCategoriesFrom_SteamClientHome.class,
			DumpVdfStructureFrom_localconfig_vdf.class,
			DumpVdfStructureFrom_sharedconfig_vdf.class);
	}

}
