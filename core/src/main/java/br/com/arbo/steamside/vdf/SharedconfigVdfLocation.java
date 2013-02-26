package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.steamclient.localfiles.steam.userdata.File_sharedconfig_vdf;

/**

/Steam/config/config.vdf

/Steam/config/dialogconfig.vdf

/Steam/config/htmlcache/
  
/Steam/appcache/
 
 */
public class SharedconfigVdfLocation {

	public static class SharedconfigVdfMissing extends RuntimeException {

		public SharedconfigVdfMissing(final Throwable e) {
			super(e);
		}

	}

	public static SharedConfig make() {
		final File from = File_sharedconfig_vdf.sharedconfig_vdf();
		final String content = readFileToString(from);
		return new SharedConfigImpl(content);
	}

	private static String readFileToString(final File from) {
		try {
			return FileUtils.readFileToString(from);
		} catch (final FileNotFoundException e) {
			throw new SharedconfigVdfMissing(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
