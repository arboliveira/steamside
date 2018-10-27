package br.com.arbo.steamside.apps;

import java.util.function.Predicate;

import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;

public class FilterKidsMode implements Predicate<App>
{

	private static boolean kidCanSeeThisCollection(App app, Kid kid)
	{
		String name = kid.getCollection().value;
		return app.isInCategory(new SteamCategory(name));
	}

	@Override
	public boolean test(App app)
	{
		return kidsModeDetector.kidsMode()
			.map(KidsMode::kid)
			.map(kid -> kidCanSeeThisCollection(app, kid))
			.orElse(true);
	}

	public FilterKidsMode(KidsModeDetector kidsModeDetector)
	{
		this.kidsModeDetector = kidsModeDetector;
	}

	private final KidsModeDetector kidsModeDetector;

}