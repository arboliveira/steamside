package br.com.arbo.steamside.api.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;

public class AppsDTO {

	public AppsDTO(
			Stream<App> apps, Limit limit,
			TagsQueries queries)
	{
		this.apps = apps;
		this.limit = limit;
		this.queries = queries;
	}

	public List<AppDTO> jsonable()
	{
		final int size = limit.limit;

		Stream<App> sorted =
				apps.sorted(new LastPlayedDescending());

		final Stream<AppDTO> narrow = sorted
				.map(this::toOptionalDTO)
				.filter(Optional::isPresent)
				.limit(size)
				.map(Optional::get);

		return narrow
				.collect(
						() -> new ArrayList<AppDTO>(size),
						ArrayList::add, ArrayList::addAll);
	}

	private Optional<AppDTO> toOptionalDTO(App app)
	{
		try
		{
			return Optional.of(AppDTOFactory.valueOf(app, queries));
		}
		catch (MissingFrom_appinfo_vdf unavailable)
		{
			return Optional.empty();
		}
	}

	private final TagsQueries queries;

	private final Stream<App> apps;

	private final Limit limit;
}