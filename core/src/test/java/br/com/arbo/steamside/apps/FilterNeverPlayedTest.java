package br.com.arbo.steamside.apps;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.types.AppName;

public class FilterNeverPlayedTest extends FilterNeverPlayed
{

	@Test
	@SuppressWarnings("static-method")
	public void neverPlayed__mustRefuse()
	{
		assertFalse(
			new FilterNeverPlayed().test(
				new AppImpl.Builder()
					.appid("1").name(new AppName("A"))
					.make().get()));
	}

}
