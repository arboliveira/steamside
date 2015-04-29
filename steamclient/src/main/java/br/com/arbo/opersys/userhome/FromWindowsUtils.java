package br.com.arbo.opersys.userhome;

import java.io.File;
import java.util.Optional;

public class FromWindowsUtils implements ProgramFiles {

	private static String envProgramFiles()
	{
		return Optional.of(
				System.getenv("ProgramFiles(x86)"))
				.orElse(
						System.getenv("ProgramFiles"));
	}

	@Override
	public File getProgramFiles()
	{
		return new File(envProgramFiles());
	}

}
