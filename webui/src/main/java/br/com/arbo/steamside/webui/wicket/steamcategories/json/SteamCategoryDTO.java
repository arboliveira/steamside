package br.com.arbo.steamside.webui.wicket.steamcategories.json;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.types.Category;

@JsonAutoDetect
public class SteamCategoryDTO {

	public SteamCategoryDTO(
			final Category category) {
		this.name = category.category;
		this.link = "/favorites/setsteam/" + this.name;
	}

	@JsonProperty
	final String name;
	@JsonProperty
	final String link;
}
