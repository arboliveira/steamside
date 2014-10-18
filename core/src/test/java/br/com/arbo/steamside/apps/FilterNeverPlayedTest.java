package br.com.arbo.steamside.apps;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;

public class FilterNeverPlayedTest extends FilterNeverPlayed {

	@Test
	@SuppressWarnings("static-method")
	public void neverPlayed__mustRefuse()
	{
		final App app = new AppImpl.Builder().appid("1").make();
		assertFalse(new FilterNeverPlayed().test(app));
	}

}
