package br.com.arbo.steamside.steam.client.localfiles.appinfo;

public class NotAvailableOnThisPlatform extends RuntimeException {

	public NotAvailableOnThisPlatform()
	{
		super("Game not available on this platform");
	}
}