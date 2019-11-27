package br.com.arbo.steamside.api.app;

import java.util.List;
import java.util.stream.Collectors;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppDTOFactory
{

	public static List<AppCardTagDTO> tags_jsonable(
		AppId appid, TagsQueries queries)
	{
		return queries.tags(appid)
			.map(CollectionI::name)
			.map(AppCardTagDTO::new).collect(Collectors.toList());
	}

}
