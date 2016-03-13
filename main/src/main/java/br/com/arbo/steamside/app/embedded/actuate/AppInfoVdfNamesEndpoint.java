package br.com.arbo.steamside.app.embedded.actuate;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

public class AppInfoVdfNamesEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return null;
	}

	public AppInfoVdfNamesEndpoint(String id)
	{
		super(id);
	}
}
