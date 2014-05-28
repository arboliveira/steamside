package br.com.arbo.steamside.steam.client.apps;

public class AppCriteria {

	public static boolean isAll(AppCriteria c)
	{
		if (c == null) return true;
		return !c.gamesOnly;
	}

	public boolean gamesOnly;

}
