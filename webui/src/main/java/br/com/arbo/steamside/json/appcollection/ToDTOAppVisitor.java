package br.com.arbo.steamside.json.appcollection;

import java.util.ArrayList;
import java.util.Collection;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.types.AppId;

public class ToDTOAppVisitor implements App.Visitor {

	private static final int limit = 27;

	public final Collection<AppDTO> collection = new ArrayList<AppDTO>(limit);

	public static class Full extends RuntimeException {
		//
	}

	private final Data_appinfo_vdf appinfo;

	public ToDTOAppVisitor(final Data_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	@Override
	public void each(final App app) {
		final AppDTO dto;
		try {
			dto = toDTO(app.appid());
		} catch (final NotFound toDTOFailed) {
			return;
		}
		collection.add(dto);
		if (collection.size() == limit) throw new Full();
	}

	private AppDTO toDTO(final AppId appid) throws NotFound {
		return AppDTO.valueOf(appid, appinfo);
	}
}