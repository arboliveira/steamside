package br.com.arbo.steamside.steam.client.localfiles.appcache;

public class UnexpectedType extends RuntimeException {

	public UnexpectedType(final byte type, final String name) {
		super("Unexpected type: '" + type +
				"' before value with name: '" + name + "'");
	}

}
