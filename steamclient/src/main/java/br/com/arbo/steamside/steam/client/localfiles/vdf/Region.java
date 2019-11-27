package br.com.arbo.steamside.steam.client.localfiles.vdf;

import java.util.Optional;

public interface Region
{

	void accept(KeyValueVisitor visitor);

	Optional<Region> region(String name);

}
