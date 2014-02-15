package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;

public class Vdf {

	public Vdf(final File file) {
		root = new RegionImpl(new RootReaderFactory(file));
	}

	public RegionImpl root() {
		return root;
	}

	class RootReaderFactory implements ReaderFactory {

		public RootReaderFactory(File file) {
			try {
				this.content = FileUtils.readFileToString(file);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public Reader newReaderPositionedInside() {
			return new StringReader(content);
		}

		private String content;
	}

	private final RegionImpl root;

}
