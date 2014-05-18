package br.com.arbo.steamside.types;

public class LastPlayed {

	public final String value;

	public LastPlayed(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
