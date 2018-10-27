package br.com.arbo.steamside.api.app;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppDTOFactory
{

	public static List<AppTagDTO> tags_jsonable(
		final AppId appid,
		TagsQueries queries)
	{
		Stream<AppTagDTO> dtos =
			queries.tags(appid)
				.map(CollectionI::name)
				.map(AppTagDTO::new);

		return dtos.collect(Collectors.toList());
	}

	public static AppDTO valueOf(AppApi app, TagsQueries queries)
	{
		final AppId appid = app.appid();

		List<AppTagDTO> list = tags_jsonable(appid, queries);

		return new AppDTO(app, list);
	}

}
