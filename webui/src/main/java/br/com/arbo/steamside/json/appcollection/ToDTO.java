package br.com.arbo.steamside.json.appcollection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

public final class ToDTO {

	private static final int limit = 27;

	private final InMemory_appinfo_vdf appinfo;

	public ToDTO(
			final InMemory_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	public AppDTO toDTO(final AppId appid) {
		final AppName appname = nameOf(appid);
		final AppDTO dto = new AppDTO(appid, appname);
		return dto;
	}

	public AppCollectionDTO convert(final List<App> list) {
		final List<AppDTO> dto = convertListAppToListAppDto(list);
		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = dto;
		return results;
	}

	private List<AppDTO> convertListAppToListAppDto(final List<App> list) {
		final List<AppDTO> dto = new ArrayList<AppDTO>(list.size());
		populate(list, dto);
		return dto;
	}

	private void populate(final List<App> list, final List<AppDTO> dto) {
		for (final App app : list) {
			if (dto.size() > limit) return;
			try {
				dto.add(toDto(app));
			} catch (final NotFound ex) {
				// do nothing
			}
		}
	}

	private AppDTO toDto(final App app) {
		final AppId appid = app.appid();
		return toDTO(appid);
	}

	AppName nameOf(final AppId appid) {
		return appinfo.get(appid.appid).name();
	}

}