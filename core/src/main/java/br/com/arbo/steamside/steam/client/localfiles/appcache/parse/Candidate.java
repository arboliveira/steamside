package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

class Candidate {

	private final String incoming;
	private String wanted;
	private String under;
	private String underDeep;
	private final String path;

	Candidate(final String incoming, String path) {
		this.incoming = incoming;
		this.path = path;
	}

	Candidate named(final String wanted) {
		this.wanted = wanted;
		this.under = null;
		this.underDeep = null;
		return this;
	}

	Candidate under(final String path) {
		this.under = path;
		return this;
	}

	Candidate underDeep(final String pathPrefix) {
		this.underDeep = pathPrefix;
		return this;
	}

	public boolean matches() {
		if (!wanted.equals(incoming))
			return false;
		if (under != null && !path.equals(under))
			return false;
		if (underDeep != null && !path.startsWith(underDeep))
			return false;
		return true;
	}
}