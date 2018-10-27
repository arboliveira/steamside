package br.com.arbo.steamside.steam.client.categories.category;

public class SteamCategory
{

	@Override
	public String toString()
	{
		return category;
	}

	public SteamCategory(final String category)
	{
		this.category = category;
	}

	public final String category;

}
