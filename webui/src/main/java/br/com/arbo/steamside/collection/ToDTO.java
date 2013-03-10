package br.com.arbo.steamside.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.App;
import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.appdto.Size;
import br.com.arbo.steamside.webui.appdto.Visible;

final class ToDTO {

	private static final int limit = 27;

	private final AppNameFactory namefactory;

	ToDTO(
			final AppNameFactory namefactory) {
		this.namefactory = namefactory;
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
		return namefactory.nameOf(appid);
	}

}