package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.localfiles.appinfo.DumpVdfStructureFrom_appinfo_vdf;

@Component
public class AppinfoVdfEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpVdfStructureFrom_appinfo_vdf.dumpToString();
	}

	@Inject
	public AppinfoVdfEndpoint(
		DumpVdfStructureFrom_appinfo_vdf dumpAppCacheContent)
	{
		super("appinfo_vdf");
		this.dumpVdfStructureFrom_appinfo_vdf = dumpAppCacheContent;
	}

	private final DumpVdfStructureFrom_appinfo_vdf dumpVdfStructureFrom_appinfo_vdf;
}