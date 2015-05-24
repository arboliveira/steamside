package br.com.arbo.steamside.steam.client.types;

public class AppType {

	public static AppType valueOf(String name)
	{
		switch (name) {
		case "game":
			return GAME;
		default:
			return new AppType(name);
		}
	}

	private AppType(final String name)
	{
		this.type = name.toLowerCase();
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

	public static final AppType GAME = new AppType("game");

	public final String type;
}
