package br.com.arbo.steamside.apps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;

public class LastPlayedDescendingTest {

	@Test
	@SuppressWarnings("static-method")
	public void neverPlayed__afterPlayed()
	{
		final App a1 = new AppImpl.Builder().appid("1").make();
		final App a2 = new AppImpl.Builder().appid("2").lastPlayed("2").make();
		assertThat(new LastPlayedDescending().compare(a1, a2),
				is(firstArgumentIsGreater));
	}

	@Test
	@SuppressWarnings("static-method")
	public void playedInThePast__afterPlayedRecently()
	{
		final App a1 = new AppImpl.Builder().appid("1").lastPlayed("1").make();
		final App a2 = new AppImpl.Builder().appid("2").lastPlayed("2").make();
		assertThat(new LastPlayedDescending().compare(a1, a2),
				is(firstArgumentIsGreater));
	}

	static final int firstArgumentIsGreater = 1;

}
