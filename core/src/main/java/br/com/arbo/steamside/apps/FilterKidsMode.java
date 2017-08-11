package br.com.arbo.steamside.apps;

import java.util.Optional;
import java.util.function.Predicate;

import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class FilterKidsMode implements Predicate<App>
{

	private static boolean kidCanSeeThisCollection(final App app, Kid kid)
	{
		String name = kid.getCollection().value;
		return app.isInCategory(new SteamCategory(name));
	}

	@Override
	public boolean test(final App app)
	{
		Optional<Kid> kid;

		try
		{
			kid = kidsmode.kid();
		}
		catch (NotInKidsMode e)
		{
			return true;
		}

		return kid.map(k -> kidCanSeeThisCollection(app, k)).orElse(true);
	}

	public FilterKidsMode(final KidsMode kidsmode)
	{
		this.kidsmode = kidsmode;
	}

	private final KidsMode kidsmode;

}