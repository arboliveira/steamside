package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheParse;

public class AppinfoVdfAppsEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return new DumpAppCacheParse().dumpToString();
	}

	public AppinfoVdfAppsEndpoint(String id)
	{
		super(id);
	}
}