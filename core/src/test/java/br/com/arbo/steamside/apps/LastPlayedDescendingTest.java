package br.com.arbo.steamside.apps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;

import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

public class LastPlayedDescendingTest
{

	@Test
	@SuppressWarnings("static-method")
	public void neverPlayed__afterPlayed()
	{
		App a1 = new AppImpl.Builder()
			.appid("1").name(new AppName("A"))
			.make().get();
		App a2 = new AppImpl.Builder()
			.appid("2").name(new AppName("B"))
			.lastPlayed(new LastPlayed("2"))
			.make().get();
		assertThat(
			comparator.compare(a1, a2),
			is(firstArgumentIsGreater));
	}

	@Test
	@SuppressWarnings("static-method")
	public void playedInThePast__afterPlayedRecently()
	{
		App a1 = new AppImpl.Builder()
			.appid("1").name(new AppName("A"))
			.lastPlayed(new LastPlayed("1"))
			.make().get();

		App a2 = new AppImpl.Builder()
			.appid("2").name(new AppName("B"))
			.lastPlayed(new LastPlayed("2"))
			.make().get();

		assertThat(
			comparator.compare(a1, a2),
			is(firstArgumentIsGreater));
	}

	static final int firstArgumentIsGreater = 1;

	private final Comparator<App> comparator =
		LastPlayed.descending(App::lastPlayed);

}
