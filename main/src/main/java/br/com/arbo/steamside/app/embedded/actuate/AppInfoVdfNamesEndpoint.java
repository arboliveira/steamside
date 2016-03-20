package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

@Component
public class AppInfoVdfNamesEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return null;
	}

	public AppInfoVdfNamesEndpoint()
	{
		super("appinfo_vdf_names");
	}

}
