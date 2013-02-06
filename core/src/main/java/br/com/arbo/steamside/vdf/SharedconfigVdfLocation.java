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

		public SharedconfigVdfMissing(Throwable e) {
			super(e);
		}

	}

	public static SharedConfig make() {
		File dir = dir();
		File from = new File(dir, "sharedconfig.vdf");
		final String content = readFileToString(from);
		return new SharedConfig(content);
	}

	private static File dir() {
		if (SystemUtils.IS_OS_WINDOWS) return windows();
		// TODO Install Steam for Linux
		return new File("etc");
	}

	private static String readFileToString(File from) {
		try {
			return FileUtils.readFileToString(from);
		} catch (FileNotFoundException e) {
			throw new SharedconfigVdfMissing(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static File windows() {
		final String envProgramFiles = envProgramFiles();
		final File userdata = new File(envProgramFiles, "Steam/userdata");
		final String userid = detect_userid(userdata);
		return new File(userdata,
				userid +
						"/7/remote");
	}

	private static String envProgramFiles() {
		final String x86 = System.getenv("ProgramFiles(x86)");
		if (x86 != null) return x86;
		return System.getenv("ProgramFiles");
	}

	private static String detect_userid(File userdata) {
		return "STEAM_USERID_DETECTED";
	}
}
