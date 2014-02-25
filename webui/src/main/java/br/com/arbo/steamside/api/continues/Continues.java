package br.com.arbo.steamside.api.continues;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor.Full;
import br.com.arbo.steamside.library.Library;

public class Continues {

	public List<AppDTO> continues() {
		final ToDTOAppVisitor visitor = new ToDTOAppVisitor(library);
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
			final Library library) {
		this.continues = continues;
		this.library = library;
	}

	@NonNull
	private final ContinuesRooster continues;
	private final Library library;
}