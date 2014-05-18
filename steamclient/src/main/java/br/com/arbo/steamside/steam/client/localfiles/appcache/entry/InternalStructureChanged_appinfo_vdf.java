package br.com.arbo.steamside.steam.client.localfiles.appcache.entry;

public class InternalStructureChanged_appinfo_vdf extends RuntimeException {

	public InternalStructureChanged_appinfo_vdf() {
		super("The internal structure of appinfo.vdf has changed"
				+ " since we last parsed it."
				+ " There is now an application which name "
				+ " is at a location the current algorithm"
				+ " is unable to parse.");
	}
}
