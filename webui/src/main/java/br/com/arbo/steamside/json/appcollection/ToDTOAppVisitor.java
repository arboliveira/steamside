package br.com.arbo.steamside.json.appcollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;

public class ToDTOAppVisitor implements Consumer<App> {

	private static final int limit = 27;

	public final Collection<AppDTO> collection = new ArrayList<AppDTO>(limit);

	public static class Full extends RuntimeException {
		//
	}

	private final Library library;

	public ToDTOAppVisitor(final Library library) {
		this.library = library;
	}

	@Override
	public void accept(final App app) {
		final AppDTO dto;
		try {
			dto = toDTO(app.appid());
		} catch (MissingFrom_appinfo_vdf toDTOFailed) {
			return;
		} catch (NotFound e) {
			return;
		}
		collection.add(dto);
		if (collection.size() == limit) throw new Full();
	}

	private AppDTO toDTO(final AppId appid) throws MissingFrom_appinfo_vdf,
			NotFound {
		return AppDTO.valueOf(appid, library);
	}
}