package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Factory_sharedconfig_vdf {

	public static Data_sharedconfig_vdf fromFile() {
		final String content =
				readFileToString(File_sharedconfig_vdf.sharedconfig_vdf());
		final Data_sharedconfig_vdf parse =
				new Parse_sharedconfig_vdf(content).parse();
		return parse;
	}

	public static class FileNotFound_sharedconfig_vdf extends RuntimeException {

		public FileNotFound_sharedconfig_vdf(final Throwable e) {
			super(e);
		}

	}

	private static String readFileToString(final File from) {
		try {
			return FileUtils.readFileToString(from);
		} catch (final FileNotFoundException e) {
			throw new FileNotFound_sharedconfig_vdf(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
