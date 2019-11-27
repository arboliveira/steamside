package br.com.arbo.steamside.api.continues;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.arbo.steamside.api.app.AppCardDTO;
import br.com.arbo.steamside.api.app.AppCardDTOBuilder;
import br.com.arbo.steamside.api.app.AppDTOFactory;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.platform.PlatformCheck;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;

public class Continues
{

	public List<AppCardDTO> continues()
	{
		return continues.continues()
			.map(this::toOptionalDTO)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.limit(apiAppSettings.limit().limit())
			.collect(Collectors.toList());
	}

	@Inject
	public Continues(
		ContinuesRooster continues,
		AppSettings apiAppSettings,
		PlatformFactory platformFactory,
		TagsQueries queries)
	{
		this.continues = continues;
		this.apiAppSettings = apiAppSettings;
		this.platformFactory = platformFactory;
		this.tagsQueries = queries;
	}

	private Optional<AppCardDTO> toOptionalDTO(App app)
	{
		return Optional.of(valueOf(app));
	}

	private AppCardDTO valueOf(App app)
	{
		return new AppCardDTOBuilder()
			.appid(app.appid())
			.name(app.name())
			.tags(AppDTOFactory.tags_jsonable(app.appid(), tagsQueries))
			.unavailable(
				!new PlatformCheck().app(app)
					.platform(platformFactory.current())
					.isAvailable())
			.build();
	}

	private final AppSettings apiAppSettings;

	private final ContinuesRooster continues;

	private final PlatformFactory platformFactory;

	private final TagsQueries tagsQueries;
}