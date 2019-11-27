package br.com.arbo.steamside.api.search;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.api.app.AppCardDTO;
import br.com.arbo.steamside.api.app.AppCardDTOBuilder;
import br.com.arbo.steamside.api.app.AppDTOFactory;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.steam.store.App;
import br.com.arbo.steamside.steam.store.SteamStoreSearch;

public class SearchController_search_json
{

	List<AppCardDTO> jsonable(String query)
	{
		List<AppCardDTO> list = new ArrayList<AppCardDTO>(20);
		SteamStoreSearch.search(
			query, app -> list.add(toDto(app)));
		return list;
	}

	SearchController_search_json(TagsData tagsData)
	{
		this.tagsData = tagsData;
	}

	private AppCardDTO toDto(App app)
	{
		return new AppCardDTOBuilder()
			.appid(app.appid)
			.name(app.name)
			.tags(AppDTOFactory.tags_jsonable(app.appid, tagsData))
			.unavailable(false)
			.build();
	}

	private final TagsData tagsData;
}