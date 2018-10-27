package br.com.arbo.steamside.steam.client.library;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;

public class GameFinderTest
{

	@Test
	public void notFound()
	{
		doReturn(appsHome).when(steamClientHome).apps();
		doReturn(Optional.empty()).when(appsHome).find(any());
		assertTrue(finder.isGame(new AppId("foo")));
	}

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		finder = new GameFinder(steamClientHome);
	}

	@Mock
	AppsHome appsHome;

	GameFinder finder;

	@Mock
	SteamClientHome steamClientHome;

}
