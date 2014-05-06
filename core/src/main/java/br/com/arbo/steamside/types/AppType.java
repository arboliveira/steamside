package br.com.arbo.steamside.types;

public class AppType {

	public AppType(final String name) {
		this.type = name.toLowerCase();
	}

	public boolean isGame()
	{
		return "game".equals(type);
	}

	@Override
	public String toString()
	{
		return type;
	}

	public final String type;
}
