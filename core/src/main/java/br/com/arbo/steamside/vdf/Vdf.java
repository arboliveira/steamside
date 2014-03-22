package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Vdf {

	static String readFileToString(File file)
	{
		try {
			return FileUtils.readFileToString(file);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Vdf(final File file) {
		String content = readFileToString(file);
		RootReaderFactory rootContext = new RootReaderFactory(content);
		root = new RegionImpl(rootContext);
	}

	public RegionImpl root()
	{
		return root;
	}

	private final RegionImpl root;
}
