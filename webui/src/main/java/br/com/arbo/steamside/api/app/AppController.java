package br.com.arbo.steamside.api.app;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.rungame.Timeout;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@RestController
@RequestMapping(br.com.arbo.steamside.mapping.App.app)
public class AppController {

	private static void letLoadingAnimationRunForJustALittleLonger()
	{
		try
		{
			Thread.sleep(4000);
		}
		catch (final InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("{appid}/" + br.com.arbo.steamside.mapping.App.run)
	public void run(
		@PathVariable String appid)
			throws NotAvailableOnThisPlatform, Timeout, NotFound
	{
		rungame.askSteamToRunGameAndWaitUntilItsUp(new AppId(appid));
		letLoadingAnimationRunForJustALittleLonger();
	}

	@RequestMapping("{appid}/tag/{collection}")
	public void tag(
		@PathVariable String appid,
		@PathVariable String collection) throws Exception
	{
		tags.tagRemember(new CollectionName(collection), new AppId(appid));
	}

	@Inject
	private RunGameCommand rungame;

	@Inject
	private TagsData tags;

}
