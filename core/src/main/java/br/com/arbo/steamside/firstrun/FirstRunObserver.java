package br.com.arbo.steamside.firstrun;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.bootstrap.Bootstrap;
import br.com.arbo.steamside.bootstrap.Event;
import br.com.arbo.steamside.bootstrap.EventObserver;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.copy.CopyAllSteamCategories;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.types.CollectionName;

public class FirstRunObserver implements EventObserver
{

	@Override
	public void onEvent(Event event)
	{
		if (event instanceof InitialLoadDetectedFirstRunEver)
		{
			getLogger().info(
				"This is the first time ever Steamside runs in this machine.");

			bootstrap.whenWired(this::copyAllSteamCategories);
		}
	}

	private void copyAllSteamCategories()
	{
		new CopyAllSteamCategories(steamsideData.tags(), library).execute();
		CollectionsData c = steamsideData.collections();
		c.favorite(c.find(new CollectionName("favorite")));
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

	@Inject
	public FirstRunObserver(
		SteamsideData steamsideData, Library library, Bootstrap bootstrap)
	{
		this.steamsideData = steamsideData;
		this.library = library;
		this.bootstrap = bootstrap;

		bootstrap.addObserver(this);
	}

	private final Bootstrap bootstrap;
	private final Library library;
	private final SteamsideData steamsideData;

}
