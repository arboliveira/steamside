package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;

public class Vdf {

	static String readFileToString(File file)
	{
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Vdf(final File file) {
		String content = readFileToString(file);
		StringReader reader = new StringReader(content);
		root = new RegionImpl(reader);
	}

	public RegionImpl root()
	{
		return root;
	}

	private final RegionImpl root;
}
