package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.webui.wicket.app.AppPage;
import br.com.arbo.steamside.webui.wicket.collection.Params;
import br.com.arbo.steamside.webui.wicket.continuejson.ContinueJson;
import br.com.arbo.steamside.webui.wicket.search.SearchJson;
import br.com.arbo.steamside.webui.wicket.session.json.SessionJson;
import br.com.arbo.steamside.webui.wicket.steamclient.SteamClientPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see br.com.arbo.steamside.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{

	public static User nextUsername;
	public static KidsMode nextKidsMode;

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	public SteamBrowserProtocol getSteamBrowserProtocol() {
		return this.container.getComponent(SteamBrowserProtocol.class);
	}

	public AppNameFactory appNameFactory() {
		return this.container.getComponent(AppNameFactory.class);
	}

	public SharedConfigConsume sharedconfig() {
		return this.container.getComponent(SharedConfigConsume.class);
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
				container.getComponent(SearchJson.class));
		mountResource(
				"/continue.json",
				container.getComponent(ContinueJson.class));
		mountResource(
				"/session.json",
				container.getComponent(SessionJson.class));

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

	public WicketApplication() {
		final MutablePicoContainer c = ContainerFactory.newContainer();
		final MutablePicoContainerX cx = new MutablePicoContainerX(c);
		cx.replaceComponent(KidsMode.class, nextKidsMode);
		cx.replaceComponent(User.class, nextUsername);
		this.container = c;
		nextUsername = null;
	}

	private final MutablePicoContainer container;

}