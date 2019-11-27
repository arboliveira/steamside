package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.DumpAppinfoVdfParse;

@Component
public class AppinfoVdfAppsEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpAppCacheParse.dumpToString();
	}

	@Inject
	public AppinfoVdfAppsEndpoint(DumpAppinfoVdfParse dumpAppCacheParse)
	{
		super("appinfo_vdf_apps");
		this.dumpAppCacheParse = dumpAppCacheParse;
	}

	private final DumpAppinfoVdfParse dumpAppCacheParse;
}