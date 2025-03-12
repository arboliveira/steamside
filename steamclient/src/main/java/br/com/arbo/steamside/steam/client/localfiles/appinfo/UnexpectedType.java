package br.com.arbo.steamside.steam.client.localfiles.appinfo;

public class UnexpectedType extends RuntimeException {

	public UnexpectedType(final byte type, final String name, final int position) {
		super("Unexpected type: '" + type +
				"' before value with name: '" + name +
				"' at position: " + position);
	}

}
