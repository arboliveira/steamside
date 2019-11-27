package br.com.arbo.steamside.steam.client.localfiles.vdf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VdfStructure
{

	public VdfStructure(InputStream in)
	{
		root = new RegionImpl(new BufferedReader(new InputStreamReader(in)));
	}

	public Region root()
	{
		return root;
	}

	private final Region root;
}
