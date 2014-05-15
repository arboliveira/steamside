package br.com.arbo.steamside.api.steamcategories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.arbo.steamside.types.SteamCategory;

@JsonAutoDetect
public class SteamCategoryDTO {

	public static List<SteamCategoryDTO> valueOf(Stream<SteamCategory> categories)
	{
		final List<SteamCategoryDTO> dto;
		dto = categories
				.map(SteamCategoryDTO::new)
				.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		return dto;
	}

	public SteamCategoryDTO(
			final SteamCategory category) {
		this.name = category.category;
		this.link = "/favorites/setsteam/" + this.name;
	}

	@JsonProperty
	final String name;
	@JsonProperty
	final String link;
}
