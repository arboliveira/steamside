package br.com.arbo.steamside.api.steamcategories;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;

@JsonAutoDetect
public class SteamCategoryDTO
{

	public SteamCategoryDTO(SteamCategory category)
	{
		this.name = category.category;
		this.link = "/favorites/setsteam/" + this.name;
	}

	@JsonProperty
	public final String link;
	@JsonProperty
	public final String name;
}
