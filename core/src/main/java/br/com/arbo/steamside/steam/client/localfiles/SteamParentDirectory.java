package br.com.arbo.steamside.steam.client.localfiles;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;

class SteamParentDirectory {

	static File parentdir() {
		if (SystemUtils.IS_OS_LINUX) return linux();
		if (SystemUtils.IS_OS_WINDOWS) return windows();
		throw new UnsupportedOperationException();
	}

	private static File linux() {
		return new File(SystemUtils.USER_HOME, ".steam");
	}

	private static File windows() {
		return new File(envProgramFiles());
	}

	private static String envProgramFiles() {
		final String x86 = System.getenv("ProgramFiles(x86)");
		if (x86 != null) return x86;
		return System.getenv("ProgramFiles");
	}

}
