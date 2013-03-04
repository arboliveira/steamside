package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import br.com.arbo.steamside.steam.client.localfiles.appcache.AppNameFromLocalFiles;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.webui.wicket.app.AppPage;
import br.com.arbo.steamside.webui.wicket.collection.Params;
import br.com.arbo.steamside.webui.wicket.continuejson.ContinueJson;
import br.com.arbo.steamside.webui.wicket.search.SearchJson;
import br.com.arbo.steamside.webui.wicket.steamclient.SteamClientPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see br.com.arbo.steamside.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{

	private final AppNameFactory appNameFactory =
			new AppNameFromLocalFiles(new InMemory_appinfo_vdf());

	private final SharedConfigConsume sharedconfig =
			new SharedConfigConsume();

	public AppNameFactory appNameFactory() {
		return appNameFactory;
	}

	public SharedConfigConsume sharedconfig() {
		return sharedconfig;
	}

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class< ? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		mountResource(
				"/search.json",
				new SearchJson());
		mountResource(
				"/continue.json",
				new ContinueJson(
						new ContinueNeedsImpl(appNameFactory, sharedconfig)));

		mountPage(
				"/app" +
						"/${" + AppPage.PARAM_appid + "}" +
						"/#{" + AppPage.PARAM_command + "}",
				AppPage.class);
		mountPage(
				"/collection" +
						"/${" + Params.PARAM_collectionname + "}" +
						"/#{" + Params.PARAM_command + "}",
				AppCollectionPage.class);
		mountPage(
				"/steamclient" +
						"/${" + SteamClientPage.PARAM_command + "}" +
						"/#{" + SteamClientPage.PARAM_argument + "}",
				SteamClientPage.class);
	}
}