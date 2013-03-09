package br.com.arbo.steamside.app.port;

public class PortAlreadyInUse extends RuntimeException {

	public PortAlreadyInUse(final Throwable e) {
		super(e);
	}
}
