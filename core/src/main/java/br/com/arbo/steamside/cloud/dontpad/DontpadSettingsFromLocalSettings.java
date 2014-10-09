package br.com.arbo.steamside.cloud.dontpad;

public class DontpadSettingsFromLocalSettings implements DontpadSettings {

	@Override
	public String url()
	{
		return "CLOUD_URL";
	}

}
