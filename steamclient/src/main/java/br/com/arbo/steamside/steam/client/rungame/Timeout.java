package br.com.arbo.steamside.steam.client.rungame;

public class Timeout extends RuntimeException {

	public Timeout()
	{
		super("Game executable never showed up");
	}
}
