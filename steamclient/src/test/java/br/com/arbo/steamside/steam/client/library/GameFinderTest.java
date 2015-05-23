package br.com.arbo.steamside.steam.client.library;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public class GameFinderTest {

	@Test
	public void notFound()
	{
		doThrow(NotFound.class).when(library).find(any());
		assertTrue(finder.isGame(new AppId("foo")));
	}

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		finder = new GameFinder(library);
	}

	@Mock
	Library library;

	GameFinder finder;

}
