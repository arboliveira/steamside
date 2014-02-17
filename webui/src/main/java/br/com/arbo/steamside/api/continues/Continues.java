package br.com.arbo.steamside.api.continues;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor.Full;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.Data_appinfo_vdf;

public class Continues {

	public List<AppDTO> continues() {
		final ToDTOAppVisitor visitor = new ToDTOAppVisitor(appinfo);
		try {
			continues.accept(visitor);
		} catch (final Full full) {
			// All right!
		}
		return new ArrayList<AppDTO>(visitor.collection);
	}

	@Inject
	public Continues(
			@NonNull final ContinuesRooster continues,
			final Data_appinfo_vdf appinfo) {
		super();
		this.continues = continues;
		this.appinfo = appinfo;
	}

	@NonNull
	private final ContinuesRooster continues;
	private final Data_appinfo_vdf appinfo;
}