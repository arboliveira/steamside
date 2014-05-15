package br.com.arbo.steamside.types;

public class SteamCategory {

	public final String category;

	public SteamCategory(final String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return category;
	}
}
