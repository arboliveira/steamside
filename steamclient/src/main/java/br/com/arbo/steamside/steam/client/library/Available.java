package br.com.arbo.steamside.steam.client.library;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public class Available {

	public Available(Library library)
	{
		this.library = library;
	}

	public Stream<App> narrow(Stream<AppId> appids)
	{
		return appids.map(this::toOptionalApp)
				.filter(Optional::isPresent)
				.map(Optional::get);
	}

	private Optional<App> toOptionalApp(AppId appid)
	{
		try
		{
			final App app = library.find(appid);
			return Optional.of(app);
		}
		catch (NotFound unavailable)
		{
			return Optional.empty();
		}
	}

	private final Library library;

}
