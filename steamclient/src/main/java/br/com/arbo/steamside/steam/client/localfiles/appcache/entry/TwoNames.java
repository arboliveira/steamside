package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

public class TwoNames extends RuntimeException {

	public TwoNames() {
		super("An application at appinfo.vdf"
				+ " has the name attribute defined"
				+ " at two places, both valid"
				+ " for the current parsing algorithm.");
	}
}
