package br.com.arbo.steamside.api.continues;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.library.Library;

public class Continues {

	@Inject
	public Continues(
			@NonNull final ContinuesRooster continues,
			@NonNull AppSettings apiAppSettings,
			final Library library, TagsQueries queries)
	{
		this.continues = continues;
		this.apiAppSettings = apiAppSettings;
		this.library = library;
		this.queries = queries;
	}

	public List<AppDTO> continues()
	{
		return new AppsDTO(
				continues.continues().map(App::appid),
				apiAppSettings.limit(), library, queries).jsonable();
	}

	@NonNull
	private final AppSettings apiAppSettings;

	private final TagsQueries queries;

	@NonNull
	private final ContinuesRooster continues;
	private final Library library;
}