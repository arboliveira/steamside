package br.com.arbo.steamside.api.continues;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.apps.App;

public class Continues {

	@Inject
	public Continues(
			@NonNull final ContinuesRooster continues,
			final Library library, CollectionsQueries queries)
	{
		this.continues = continues;
		this.library = library;
		this.queries = queries;
	}

	public List<AppDTO> continues()
	{
		return new AppsDTO(
				continues.continues().map(App::appid), library, queries).jsonable();
	}

	private final CollectionsQueries queries;

	@NonNull
	private final ContinuesRooster continues;
	private final Library library;
}