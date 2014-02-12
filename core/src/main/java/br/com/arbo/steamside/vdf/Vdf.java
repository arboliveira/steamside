package br.com.arbo.steamside.vdf;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf.FileNotFound_sharedconfig_vdf;

public class Vdf implements Closeable {

	private String content;
	private final RegionImpl root;

	public Vdf(final File file) throws IOException {
		this.content = readFileToString(file);
		this.root = new RegionImpl(new RootReaderFactory());
	}

	public RegionImpl root() {
		return root;
	}

	class RootReaderFactory implements ReaderFactory {

		@Override
		public Reader readerPositionedInside() {
			return Vdf.this.readerPositionedInside();
		}

	}

	Reader readerPositionedInside() {
		return new StringReader(content);
	}

	private static String readFileToString(final File from) throws IOException {
		try {
			return FileUtils.readFileToString(from);
		} catch (final FileNotFoundException e) {
			throw new FileNotFound_sharedconfig_vdf(e);
		}
	}

	@Override
	public void close() throws IOException {
		this.content = null;
	}

}
