package br.com.arbo.steamside.steam.client.vdf;

import java.util.Optional;

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
		if (under.isPresent() && !path.equals(under.get()))
			return false;
		if (underDeep.isPresent() && !path.startsWith(underDeep.get()))
			return false;
		return true;
	}

	public Candidate named(String wanted)
	{
		this.wanted = wanted;
		this.under = Optional.empty();
		this.underDeep = Optional.empty();
		return this;
	}

	public Candidate under(String path)
	{
		this.under = Optional.of(path);
		return this;
	}

	public Candidate underDeep(String pathPrefix)
	{
		this.underDeep = Optional.of(pathPrefix);
		return this;
	}

	private final String incoming;

	private String wanted;

	private Optional<String> under = Optional.empty();

	private Optional<String> underDeep = Optional.empty();

	private final String path;
}