package br.com.arbo.steamside.api.app;

public class AppSettingsImpl implements AppSettings {

	@Override
	public Limit limit()
	{
		return new Limit(60);
	}

}
