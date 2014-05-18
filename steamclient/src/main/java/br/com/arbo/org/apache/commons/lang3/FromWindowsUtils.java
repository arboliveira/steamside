package br.com.arbo.org.apache.commons.lang3;

import java.io.File;

public class FromWindowsUtils implements ProgramFiles {

	@Override
	public File getProgramFiles() {
		return new File(envProgramFiles());
	}

	private static String envProgramFiles() {
		final String x86 = System.getenv("ProgramFiles(x86)");
		if (x86 != null) return x86;
		return System.getenv("ProgramFiles");
	}

}
