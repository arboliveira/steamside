package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import br.com.arbo.steamside.webui.wicket.app.AppPage;
import br.com.arbo.steamside.webui.wicket.collection.Params;
import br.com.arbo.steamside.webui.wicket.search.SearchJsonResourceReference;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see br.com.arbo.steamside.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{

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

		mountResource("/search", new SearchJsonResourceReference());

		mountPage("/app" +
				"/${" + AppPage.PARAM_appid + "}" +
				"/#{" + AppPage.PARAM_command + "}", AppPage.class);
		mountPage("/collection" +
				"/${" + Params.PARAM_collectionname + "}" +
				"/#{" + Params.PARAM_command + "}",
				AppCollectionPage.class);

		// add your configuration here
	}
}