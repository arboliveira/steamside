package br.com.arbo.steamside.types;

public class AppName {

	public final String name;

	public AppName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
