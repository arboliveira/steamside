package br.com.arbo.steamside.api.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;

public class AppsDTO {

	public static List<AppDTO> valueOf(Stream<App> apps, Library library)
	{
		return new AppsDTO(library).toAppsDTO(apps);
	}

	private AppsDTO(final Library library) {
		this.library = library;
	}

	private void add(final App app)
	{
		final AppDTO dto;
		try {
			dto = toDTO(app.appid());
		}
		catch (MissingFrom_appinfo_vdf toDTOFailed) {
			return;
		}
		catch (NotFound e) {
			return;
		}
		collection.add(dto);
	}

	private List<AppDTO> toAppsDTO(Stream<App> apps)
	{
		apps.limit(limit).forEach(this::add);
		return collection;
	}

	private AppDTO toDTO(final AppId appid)
		throws MissingFrom_appinfo_vdf, NotFound
	{
		return AppDTO.valueOf(appid, library);
	}

	private static final int limit = 27;

	private final List<AppDTO> collection = new ArrayList<AppDTO>(limit);

	private final Library library;
}