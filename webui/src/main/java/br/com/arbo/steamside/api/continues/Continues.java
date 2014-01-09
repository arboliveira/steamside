package br.com.arbo.steamside.api.continues;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.ContinuesQuery;
import br.com.arbo.steamside.continues.FilterContinues;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor.Full;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

public class Continues {

	public List<AppDTO> continues() {
		final ToDTOAppVisitor visitor = new ToDTOAppVisitor(appinfo);
		try {
			new ContinuesQuery(from, continues).accept(visitor);
		} catch (final Full full) {
			// All right!
		}
		return new ArrayList<AppDTO>(visitor.dto);
	}

	@Inject
	public Continues(
			@NonNull final FilterContinues continues,
			final InMemory_appinfo_vdf appinfo,
			final CollectionFromVdf from) {
		super();
		this.continues = continues;
		this.appinfo = appinfo;
		this.from = from;
	}

	@NonNull
	private final FilterContinues continues;
	private final InMemory_appinfo_vdf appinfo;
	private final CollectionFromVdf from;
}