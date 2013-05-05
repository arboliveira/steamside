package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.appdto.Size;
import br.com.arbo.steamside.webui.appdto.Visible;

final class ToDTO {

	private static final int limit = 27;

	private final InMemory_appinfo_vdf appinfo;

	ToDTO(
			final InMemory_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	AppCollectionDTO sortLimitConvert(final List<App> list) {
		Collections.sort(list, new App.LastPlayedDescending());
		final List<AppDTO> listdto = convertListAppToListAppDto(list);
		final AppCollectionDTO results = new AppCollectionDTO();
		results.apps = listdto;
		return results;
	}

	private List<AppDTO> convertListAppToListAppDto(final List<App> list) {
		final List<AppDTO> listdto = new ArrayList<AppDTO>(list.size());
		int i = 0;
		for (final App app : list) {
			i++;
			if (i > limit) break;
			listdto.add(toDto(app, i));
		}
		return listdto;
	}

	private AppDTO toDto(final App app, final int i) {
		final AppId appid = app.appid();
		final AppName appname = nameOf(appid);
		final Size size = i == 1 ? Size.Large : Size.Regular;
		final Visible visible = i <= 3 ? Visible.True : Visible.False;
		final AppDTO dto = new AppDTO(appid, appname, size, visible);
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