package br.com.arbo.steamside.steam.client.types;

public class AppNameTypes {

	public static String appnametype(AppName name, AppType type)
	{
		String n = name.name;
		if (type.isGame()) return n;
		return n + " (" + type.type + ")";
	}

}
