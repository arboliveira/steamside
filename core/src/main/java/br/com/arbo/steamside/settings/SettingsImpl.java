package br.com.arbo.steamside.settings;

public class SettingsImpl implements Settings {

	@Override
	public boolean currentPlatformOnly()
	{
		return false;
	}

	@Override
	public boolean gamesOnly()
	{
		return true;
	}

}
