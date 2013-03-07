package br.com.arbo.steamside.app.port;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class FreePortLocator implements Port {

	@Override
	public int port() {
		for (int p = RANGE_BEGIN; p <= RANGE_BEGIN + CANDIDATES; p++)
			if (isFree(p)) return p;
		throw new RuntimeException("no ports available");
	}

	private boolean isFree(final int p) {
		final URL proof = proof(p);
		final String there;
		try {
			there = IOUtils.toString(proof.openStream(), "UTF-8");
		} catch (final IOException e) {
			return true;
		}
		return false;
	}

	private static URL proof(final int p) {
		try {
			return new URL("http://localhost:" + p);
		} catch (final MalformedURLException e1) {
			throw new RuntimeException(e1);
		}
	}

	private static final int RANGE_BEGIN = 42424;
	private static final int CANDIDATES = 10;

}
