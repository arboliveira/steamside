package br.com.arbo.steamside.apps;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;

public class FilterNeverPlayedTest extends FilterNeverPlayed {

	@Test(expected = Reject.class)
	@SuppressWarnings("static-method")
	public void neverPlayed__mustRefuse() throws Reject {
		final App app = new AppImpl.Builder().appid("1").make();
		new FilterNeverPlayed().consider(app);
	}

}
