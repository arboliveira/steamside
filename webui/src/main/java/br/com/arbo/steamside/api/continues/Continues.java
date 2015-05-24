package br.com.arbo.steamside.api.continues;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.api.app.AppApiApp;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppDTOListBuilder;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.steam.client.apps.App;

public class Continues {

	@Inject
	public Continues(
		ContinuesRooster continues,
		AppSettings apiAppSettings,
		TagsQueries queries)
	{
		this.continues = continues;
		this.apiAppSettings = apiAppSettings;
		this.queries = queries;
	}

	public List<AppDTO> continues()
	{
		Stream<App> apps = continues.continues();

		Stream<AppApi> cards = apps.map(AppApiApp::new);

		AppDTOListBuilder builder = new AppDTOListBuilder();
		builder.cards(cards);
		builder.limit(apiAppSettings.limit());
		builder.tagsQueries(queries);
		return builder.build();
	}

	private final AppSettings apiAppSettings;

	private final TagsQueries queries;

	private final ContinuesRooster continues;
}