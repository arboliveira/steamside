package br.com.arbo.steamside.api.collection;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.library.GameFinder;
import br.com.arbo.steamside.steam.client.library.Library;

public class GameFinderTest {

	@Test
	public void notFound()
	{
		doThrow(NotFound.class).when(library).find(any());
		assertFalse(finder.isGame(null));
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
