package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


/**

/Steam/config/config.vdf

/Steam/config/dialogconfig.vdf

/Steam/config/htmlcache/
 
 */
public class Factory_sharedconfig_vdf {

	public static class FileNotFound_sharedconfig_vdf extends RuntimeException {

		public FileNotFound_sharedconfig_vdf(final Throwable e) {
			super(e);
		}

	}

	public static Data_sharedconfig_vdf fromFile() {
		final File from = File_sharedconfig_vdf.sharedconfig_vdf();
		final String content = readFileToString(from);
		final Data_sharedconfig_vdf parse =
				new Parse_sharedconfig_vdf(content).parse();
		return parse;
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
