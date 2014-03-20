package br.com.arbo.steamside.api.continues;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.library.Library;

public class Continues {

	@Inject
	public Continues(
			@NonNull final ContinuesRooster continues,
			final Library library) {
		this.continues = continues;
		this.library = library;
	}

	public List<AppDTO> continues()
	{
		return AppsDTO.valueOf(continues.continues(), library);
	}

	@NonNull
	private final ContinuesRooster continues;
	private final Library library;
}