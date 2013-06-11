package br.com.arbo.steamside.json.appcollection;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

public final class ToDTO {

	private static final int limit = 27;

	private final InMemory_appinfo_vdf appinfo;

	public ToDTO(
			final InMemory_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	public AppCollectionDTO convert(final List<App> list) {
		final List<AppDTO> dto = convertListAppToListAppDto(list);
		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = dto;
		return results;
	}

	private List<AppDTO> convertListAppToListAppDto(final List<App> list) {
		final List<AppDTO> dto = new ArrayList<AppDTO>(list.size());
		int i = 0;
		for (final App app : list) {
			i++;
			if (i > limit) break;
			dto.add(toDto(app));
		}
		return dto;
	}

	private AppDTO toDto(final App app) {
		final AppId appid = app.appid();
		final AppName appname = nameOf(appid);
		final AppDTO dto = new AppDTO(appid, appname);
		return dto;
	}

	AppName nameOf(final AppId appid) {
		try {
			return appinfo.get(appid.appid).name();
		} catch (final NotFound e) {
			return new AppName(e.getMessage());
		}
	}

}