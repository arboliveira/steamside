package br.com.arbo.steamside.apps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LastPlayedDescendingTest {

	@Test
	@SuppressWarnings("static-method")
	public void playedInThePast__afterPlayedRecently() {
		final App a1 = new AppImpl.Builder().appid("1").lastPlayed("1").make();
		final App a2 = new AppImpl.Builder().appid("2").lastPlayed("2").make();
		final int firstArgumentIsGreater = 1;
		assertThat(new LastPlayedDescending().compare(a1, a2),
				is(firstArgumentIsGreater));
	}

	@Test(expected = NeverPlayed.class)
	@SuppressWarnings("static-method")
	public void neverPlayed__mustRefuse() {
		final App a1 = new AppImpl.Builder().appid("1").make();
		final App a2 = null;
		new LastPlayedDescending().compare(a1, a2);
	}

}
