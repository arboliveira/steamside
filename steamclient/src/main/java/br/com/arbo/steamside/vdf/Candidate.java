package br.com.arbo.steamside.vdf;

public class Candidate {

	public Candidate(final String incoming, String path)
	{
		this.incoming = incoming;
		this.path = path;
	}

	public boolean matches()
	{
		if (!wanted.equals(incoming))
			return false;
		if (under != null && !path.equals(under))
			return false;
		if (underDeep != null && !path.startsWith(underDeep))
			return false;
		return true;
	}

	public Candidate named(final String wanted)
	{
		this.wanted = wanted;
		this.under = null;
		this.underDeep = null;
		return this;
	}

	public Candidate under(final String path)
	{
		this.under = path;
		return this;
	}

	public Candidate underDeep(final String pathPrefix)
	{
		this.underDeep = pathPrefix;
		return this;
	}

	private final String incoming;

	private String wanted;

	private String under;

	private String underDeep;

	private final String path;
}