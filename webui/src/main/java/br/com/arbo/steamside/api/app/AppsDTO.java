package br.com.arbo.steamside.api.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;

public class AppsDTO {

	public static List<AppDTO> valueOf(Stream<App> apps, Library library)
	{
		return new AppsDTO(library).valueOfApps(apps);
	}

	public static List<AppDTO>
			valueOfAppIds(Stream<AppId> apps, Library library)
	{
		return new AppsDTO(library).valueOfAppIds(apps);
	}

	private AppsDTO(final Library library) {
		this.library = library;
	}

	private Optional<AppDTO> toOptionalDTO(AppId appid)
	{
		try {
			return Optional.of(AppDTO.valueOf(appid, library));
		} catch (MissingFrom_appinfo_vdf | NotFound unavailable) {
			return Optional.empty();
		}
	}

	private List<AppDTO> valueOfAppIds(Stream<AppId> appids)
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

	private List<AppDTO> valueOfApps(Stream<App> apps)
	{
		return valueOfAppIds(apps.map(App::appid));
	}

	private static final int limit = 27;

	private final Library library;
}