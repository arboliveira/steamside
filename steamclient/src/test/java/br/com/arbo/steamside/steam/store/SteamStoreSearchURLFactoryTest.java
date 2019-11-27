package br.com.arbo.steamside.steam.store;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

public class SteamStoreSearchURLFactoryTest
{

	@Test
	public void url()
	{
		Assert.assertThat(
			String.valueOf(
				SteamStoreSearchURLFactory.getSteamStoreSearchURL("residue")),
			equalTo(
				"https://store.steampowered.com/search/results?term=residue"));
	}

}
