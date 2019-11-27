package br.com.arbo.steamside.steam.client.internal.apps.home;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

public class InMemoryAppsHomeTest
{

	@Test
	public void in()
	{
		home.add(
			new AppImpl.Builder()
				.appid("1").name(new AppName("A"))
				.make().get());
		home.add(
			new AppImpl.Builder()
				.appid("2").name(new AppName("B"))
				.make().get());

		List<App> collect =
			home.find(Stream.of(new AppId("1")), new AppCriteria())
				.collect(Collectors.toList());

		Assert.assertEquals(1, collect.size());
	}

	InMemoryAppsHome home = new InMemoryAppsHome();

}
