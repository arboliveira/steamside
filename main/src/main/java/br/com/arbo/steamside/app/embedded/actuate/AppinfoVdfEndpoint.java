package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheContent;

@Component
public class AppinfoVdfEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpAppCacheContent.dumpToString();
	}

	@Inject
	public AppinfoVdfEndpoint(DumpAppCacheContent dumpAppCacheContent)
	{
		super("appinfo_vdf");
		this.dumpAppCacheContent = dumpAppCacheContent;
	}

	private final DumpAppCacheContent dumpAppCacheContent;
}