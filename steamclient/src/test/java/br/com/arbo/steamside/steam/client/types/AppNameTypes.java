package br.com.arbo.steamside.steam.client.types;

public class AppNameTypes {

	public static String appnametype(AppName name, AppType type)
	{
		final String n = name.name;
		final String t = type.type;
		if ("game".equals(t)) return n;
		return n + " (" + t + ")";
	}

}
