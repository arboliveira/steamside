package br.com.arbo.steamside.steam.client.types;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppTypeTest
{

	@Test
	@SuppressWarnings("static-method")
	public void gameFlyweight()
	{
		AppType a = AppType.valueOf("GAME");
		AppType b = AppType.valueOf("game");
		AppType c = AppType.GAME;
		assertTrue(a == b && b == c);
	}

}
