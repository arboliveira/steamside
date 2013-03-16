package br.com.arbo.steamside.vdf;

public interface Region {

	void accept(final KeyValueVisitor visitor);

}
