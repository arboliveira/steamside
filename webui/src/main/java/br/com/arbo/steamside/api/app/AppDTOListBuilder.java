package br.com.arbo.steamside.api.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;

public class AppDTOListBuilder
{

	public List<AppDTO> build()
	{
		Stream<AppApi> sorted =
			cards.sorted(new LastPlayedDescending());

		Stream<AppDTO> narrow =
			sorted
				.map(this::toOptionalDTO)
				.filter(Optional::isPresent)
				.limit(limit.limit)
				.map(Optional::get);

		return narrow.collect(Collectors.toList());
	}

	public AppDTOListBuilder cards(Stream<AppApi> cards)
	{
		this.cards = cards;
		return this;
	}

	public AppDTOListBuilder limit(Limit limit)
	{
		this.limit = limit;
		return this;
	}

	public AppDTOListBuilder tagsQueries(TagsQueries tagsQueries)
	{
		this.tagsQueries = tagsQueries;
		return this;
	}

	private Optional<AppDTO> toOptionalDTO(AppApi app)
	{
		try
		{
			return Optional.of(AppDTOFactory.valueOf(app, tagsQueries));
		}
		catch (MissingFrom_appinfo_vdf unavailable)
		{
			return Optional.empty();
		}
	}

	private Stream<AppApi> cards;
	private Limit limit;
	private TagsQueries tagsQueries;

}
