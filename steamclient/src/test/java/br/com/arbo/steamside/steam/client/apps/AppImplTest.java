package br.com.arbo.steamside.steam.client.apps;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.AppImpl.Builder;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.internal.platform.PlatformImpl;
import br.com.arbo.steamside.steam.client.types.AppName;

public class AppImplTest
{

	@Test
	public void addCategory()
	{
		AppImpl a = builder().appid("foo")
			.addCategory("foo")
			.make().get();

		Assert.assertTrue(a.isInCategory(new SteamCategory("foo")));
	}

	@Test
	public void appid()
	{
		AppImpl a = builder().appid("foo").make().get();

		Assert.assertThat("foo", CoreMatchers.equalTo(a.appid().appid()));
	}

	@Test(expected = NullPointerException.class)
	public void appid_null()
	{
		builder().make();
	}

	private static Builder builder()
	{
		return new AppImpl.Builder()
			.name(new AppName("foo"));
	}

	@Test
	public void empty()
	{
		AppImpl a = builder().appid("foo").make().get();

		a.appid();
		a.cloudEnabled();
		a.executable(new PlatformImpl("foo"));
		a.executables();
		a.isInCategory(new SteamCategory("foo"));
		a.isOwned();
		a.lastPlayed();
		a.name();
		a.toString();
		a.type();
	}

	@Test
	public void incomplete()
	{
		Optional<AppImpl> app = new AppImpl.Builder().make();

		Assert.assertFalse(app.isPresent());
	}

}
