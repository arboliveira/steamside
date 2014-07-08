package br.com.arbo.steamside.api.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppsDTO {

	public AppsDTO(
			Stream<AppId> appids, Limit limit,
			final Library library, TagsQueries queries)
	{
		this.appids = appids;
		this.limit = limit;
		this.library = library;
		this.queries = queries;
	}

	public List<AppDTO> jsonable()
	{
		final int size = limit.limit;

		return appids
				.map(this::toOptionalDTO)
				.filter(Optional::isPresent)
				.limit(size)
				.map(Optional::get)
				.collect(
						() -> new ArrayList<AppDTO>(size),
						ArrayList::add, ArrayList::addAll);
	}

	private Optional<AppDTO> toOptionalDTO(AppId appid)
	{
		try
		{
			return Optional.of(AppDTOFactory.valueOf(appid, library, queries));
		}
		catch (MissingFrom_appinfo_vdf | NotFound unavailable)
		{
			return Optional.empty();
		}
	}

	private final TagsQueries queries;

	private final Stream<AppId> appids;

	private final Limit limit;

	private final Library library;
}