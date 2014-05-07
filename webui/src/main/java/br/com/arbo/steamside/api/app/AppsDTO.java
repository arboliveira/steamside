package br.com.arbo.steamside.api.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;

public class AppsDTO {

	public AppsDTO(Stream<AppId> appids, final Library library)
	{
		this.appids = appids;
		this.library = library;
	}

	public List<AppDTO> jsonable()
	{
		return appids
				.map(this::toOptionalDTO)
				.filter(Optional::isPresent)
				.limit(limit)
				.map(Optional::get)
				.collect(
						() -> new ArrayList<AppDTO>(limit),
						ArrayList::add, ArrayList::addAll);
	}

	private Optional<AppDTO> toOptionalDTO(AppId appid)
	{
		try {
			return Optional.of(AppDTO.valueOf(appid, library));
		} catch (MissingFrom_appinfo_vdf | NotFound unavailable) {
			return Optional.empty();
		}
	}

	private final Stream<AppId> appids;

	private static final int limit = 27;

	private final Library library;
}