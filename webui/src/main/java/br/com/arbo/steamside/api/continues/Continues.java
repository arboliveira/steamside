package br.com.arbo.steamside.api.continues;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppSettings;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.continues.ContinuesRooster;

public class Continues {

	@Inject
	public Continues(
			@NonNull final ContinuesRooster continues,
			@NonNull AppSettings apiAppSettings,
			TagsQueries queries)
	{
		this.continues = continues;
		this.apiAppSettings = apiAppSettings;
		this.queries = queries;
	}

	public List<AppDTO> continues()
	{
		return new AppsDTO(
				continues.continues(),
				apiAppSettings.limit(), queries).jsonable();
	}

	@NonNull
	private final AppSettings apiAppSettings;

	private final TagsQueries queries;

	@NonNull
	private final ContinuesRooster continues;
}