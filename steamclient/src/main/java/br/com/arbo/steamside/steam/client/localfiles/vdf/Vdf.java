package br.com.arbo.steamside.steam.client.localfiles.vdf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Vdf {

	public Vdf(final InputStream in)
	{
		root = new RegionImpl(new BufferedReader(new InputStreamReader(in)));
	}

	public RegionImpl root()
	{
		return root;
	}

	private final RegionImpl root;
}
