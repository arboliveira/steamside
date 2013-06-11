package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.webui.wicket.app.AppPage;
import br.com.arbo.steamside.webui.wicket.collection.Params;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see br.com.arbo.steamside.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	public static MutablePicoContainerX nextContainer;

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	public static MutablePicoContainerX getContainer() {
		return get().container;
	}

	public void exit() {
		this.container.getComponent(Exit.class).exit();
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class< ? extends WebPage> getHomePage()
	{
		return Steamside.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

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
	}

	public WicketApplication() {
		this.container = nextContainer;
		nextContainer = null;
	}

	private final MutablePicoContainerX container;

}