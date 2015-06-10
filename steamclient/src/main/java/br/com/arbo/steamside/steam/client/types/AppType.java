package br.com.arbo.steamside.steam.client.types;

public class AppType
{

	public static AppType valueOf(String name)
	{
		String normalized = name.toLowerCase();
		switch (normalized)
		{
		case "game":
			return GAME;
		default:
			return new AppType(normalized);
		}
	}

	private AppType(String name)
	{
		this.type = name;
	}

	public boolean isGame()
	{
		return this == GAME;
	}

	@Override
	public String toString()
	{
		return type;
	}

	public final String type;

	public static final AppType GAME = new AppType("game");
}
