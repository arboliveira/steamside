package br.com.arbo.steamside.api.continues;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.AppCollectionDTO;
import br.com.arbo.steamside.json.appcollection.ToDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

public class Continues {

	public List<AppDTO> continues() {
		@SuppressWarnings("null")
		final List<App> list = from.query(continues);
		sort(list);
		final AppCollectionDTO dto = new ToDTO(appinfo).convert(list);
		return dto.apps;
	}

	private static void sort(final List<App> list) {
		Collections.sort(list, new App.LastPlayedDescending());
		// TODO Prioritize games launched by current user
	}

	@Inject
	public Continues(
			@NonNull final Continue continues,
			final InMemory_appinfo_vdf appinfo,
			final CollectionFromVdf from) {
		super();
		this.continues = continues;
		this.appinfo = appinfo;
		this.from = from;
	}

	private final Continue continues;
	private final InMemory_appinfo_vdf appinfo;
	private final CollectionFromVdf from;
}