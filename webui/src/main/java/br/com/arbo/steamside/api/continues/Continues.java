package br.com.arbo.steamside.api.continues;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.api.app.AppApi;
import br.com.arbo.steamside.api.app.AppApiApp;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.continues.ContinuesRooster;

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
		final Stream<AppApi> apps = continues.continues()
			.map(AppApiApp::new);

		return new AppsDTO(
			apps,
			apiAppSettings.limit(), queries).jsonable();
	}

	private final AppSettings apiAppSettings;

	private final TagsQueries queries;

	private final ContinuesRooster continues;
}