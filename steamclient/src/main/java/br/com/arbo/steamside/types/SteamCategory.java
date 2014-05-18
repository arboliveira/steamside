package br.com.arbo.steamside.types;

public class SteamCategory {

	public SteamCategory(final String category)
	{
		this.category = category;
	}

	@Override
	public String toString()
	{
		return category;
	}

	public static class NotFound extends RuntimeException {
		//
	}

	public final String category;
}
