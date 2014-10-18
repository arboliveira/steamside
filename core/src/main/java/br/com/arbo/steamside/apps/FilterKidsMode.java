package br.com.arbo.steamside.apps;

import java.util.function.Predicate;

import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class FilterKidsMode implements Predicate<App> {

	private static boolean kidCanSeeThisCollection(final App app, Kid kid)
	{
		String name = kid.getCollection().value;
		return app.isInCategory(new SteamCategory(name));
	}

	public FilterKidsMode(final KidsMode kidsmode)
	{
		this.kidsmode = kidsmode;
	}

	@Override
	public boolean test(final App app)
	{
		final Kid kid;

		try
		{
			kid = kidsmode.kid();
		}
		catch (NotInKidsMode e)
		{
			return true;
		}

		return kidCanSeeThisCollection(app, kid);
	}

	private final KidsMode kidsmode;

}