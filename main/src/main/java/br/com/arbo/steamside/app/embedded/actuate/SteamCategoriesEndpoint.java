package br.com.arbo.steamside.app.embedded.actuate;

import javax.inject.Inject;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

import br.com.arbo.steamside.steam.client.library.DumpSteamCategoriesFrom_Library;

@Component
public class SteamCategoriesEndpoint extends AbstractEndpoint<String>
{

	@Override
	public String invoke()
	{
		return dumpSteamCategoriesFrom_Library.dumpToString();
	}

	@Inject
	public SteamCategoriesEndpoint(
		DumpSteamCategoriesFrom_Library dumpSteamCategoriesFrom_Library)
	{
		super("steamcategories");
		this.dumpSteamCategoriesFrom_Library = dumpSteamCategoriesFrom_Library;
	}

	private final DumpSteamCategoriesFrom_Library dumpSteamCategoriesFrom_Library;

}
