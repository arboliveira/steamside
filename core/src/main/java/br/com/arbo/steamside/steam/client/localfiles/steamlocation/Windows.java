package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

public class Windows implements SteamLocation {

	@Override
	public File steam() {
		return new File(parent(), "Steam");
	}

	private static File parent() {
		return new File(envProgramFiles());
	}

	private static String envProgramFiles() {
		final String x86 = System.getenv("ProgramFiles(x86)");
		if (x86 != null) return x86;
		return System.getenv("ProgramFiles");
	}
}
