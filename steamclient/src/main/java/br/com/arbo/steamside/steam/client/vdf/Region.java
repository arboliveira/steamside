package br.com.arbo.steamside.steam.client.vdf;

public interface Region {

	void accept(final KeyValueVisitor visitor);

	Region region(String name) throws NotFound;

}
