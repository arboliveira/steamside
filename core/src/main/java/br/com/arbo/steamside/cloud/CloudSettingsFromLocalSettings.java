package br.com.arbo.steamside.cloud;

public class CloudSettingsFromLocalSettings implements CloudSettings {

	@Override
	public boolean isEnabled()
	{
		return true;
	}

}
