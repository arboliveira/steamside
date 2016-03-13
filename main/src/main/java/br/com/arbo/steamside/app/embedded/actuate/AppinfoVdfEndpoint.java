package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheContent;

public class AppinfoVdfEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return new DumpAppCacheContent().dumpToString();
	}

	public AppinfoVdfEndpoint(String id)
	{
		super(id);
	}
}