package br.com.arbo.steamside.json.appcollection;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.I_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.NotFound;
import br.com.arbo.steamside.types.AppId;

public class ToDTOAppVisitor implements AppVisitor {

	private static final int limit = 27;

	public final Collection<AppDTO> dto = new ArrayBlockingQueue<AppDTO>(limit);

	public static class Full extends RuntimeException {
		//
	}

	private final I_appinfo_vdf appinfo;

	public ToDTOAppVisitor(final I_appinfo_vdf appinfo) {
		this.appinfo = appinfo;
	}

	@Override
	public void each(final App app) {
		try {
			dto.add(toDTO(app.appid()));
		} catch (final NotFound toDTOFailed) {
			// do nothing
		} catch (final IllegalStateException addFailed) {
			throw new Full();
		}
	}

	private AppDTO toDTO(final AppId appid) {
		return AppDTO.valueOf(appid, appinfo);
	}
}