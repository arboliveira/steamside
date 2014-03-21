package br.com.arbo.steamside.api.app;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.rungame.Timeout;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@Controller
@RequestMapping(br.com.arbo.steamside.mapping.App.app)
public class AppController {

	private static void letLoadingAnimationRunForJustALittleLonger()
	{
		try {
			Thread.sleep(4000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("static-method")
	@RequestMapping("{appid}/" + br.com.arbo.steamside.mapping.App.run)
	public void run(
			@NonNull @PathVariable final String appid
			) throws NotAvailableOnThisPlatform, Timeout, NotFound
	{

		rungame.askSteamToRunGameAndWaitUntilItsUp(new AppId(appid));
		letLoadingAnimationRunForJustALittleLonger();

	}

	@SuppressWarnings("static-method")
	@RequestMapping("{appid}/tag/{collection}")
	@ResponseBody
	public void tag(
			@NonNull @PathVariable final String appid,
			@NonNull @PathVariable final String collection
			) throws Exception
	{
		data.tag(new CollectionName(collection), new AppId(appid));
	}

	@Inject
	private RunGame rungame;

	@Inject
	private CollectionsData data;

}
