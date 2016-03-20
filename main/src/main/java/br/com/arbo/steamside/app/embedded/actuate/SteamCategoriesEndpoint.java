package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.library.DumpSteamCategoriesFrom_Library;
import br.com.arbo.steamside.steam.client.library.Library;

@Component
public class SteamCategoriesEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return new DumpSteamCategoriesFrom_Library(library)
			.dumpToString();
	}

	@Inject
	public SteamCategoriesEndpoint(Library library)
	{
		super("steamcategories");
		this.library = library;
	}

	private final Library library;

}
