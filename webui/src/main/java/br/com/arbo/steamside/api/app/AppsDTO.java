package br.com.arbo.steamside.api.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;

public class AppsDTO {

	public static List<AppDTO> valueOf(Stream<App> apps, Library library)
	{
		return new AppsDTO(library).toAppsDTO(apps);
	}

	private AppsDTO(final Library library) {
		this.library = library;
	}

	private List<AppDTO> toAppsDTO(Stream<App> apps)
	{
		return apps
				.map(this::toOptionalDTO)
				.filter(Optional::isPresent)
				.limit(limit)
				.map(Optional::get)
				.collect(
						() -> new ArrayList<AppDTO>(limit),
						ArrayList::add, ArrayList::addAll);
	}

	private Optional<AppDTO> toOptionalDTO(App app)
	{
		try {
			return Optional.of(AppDTO.valueOf(app.appid(), library));
		}
		catch (MissingFrom_appinfo_vdf | NotFound unavailable) {
			return Optional.empty();
		}
	}

	private static final int limit = 27;

	private final Library library;
}