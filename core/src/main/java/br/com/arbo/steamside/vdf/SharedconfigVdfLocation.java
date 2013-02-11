package br.com.arbo.steamside.vdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

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
		final File parent = parentdir();
		final File userdata = new File(parent, "steam/userdata");
		final String userid = detect_userid(userdata);
		final File userid7remote = new File(userdata, userid + "/7/remote");
		final File from = new File(userid7remote, "sharedconfig.vdf");
		final String content = readFileToString(from);
		return new SharedConfig(content);
	}

	private static File parentdir() {
		if (SystemUtils.IS_OS_WINDOWS) return windows();
		if (SystemUtils.IS_OS_LINUX) return linux();
		throw new UnsupportedOperationException();
	}

	private static File windows() {
		return new File(envProgramFiles());
	}

	private static File linux() {
		return new File(SystemUtils.USER_HOME, ".steam");
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

	private static String envProgramFiles() {
		final String x86 = System.getenv("ProgramFiles(x86)");
		if (x86 != null) return x86;
		return System.getenv("ProgramFiles");
	}

	private static String detect_userid(final File userdata) {
		return "STEAM_USERID_DETECTED";
	}
}
