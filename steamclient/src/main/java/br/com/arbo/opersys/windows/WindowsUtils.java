package br.com.arbo.opersys.windows;

import java.io.File;

public class WindowsUtils
{

	public static File getProgramFiles()
	{
		return getenvFile("ProgramFiles");
	}

	public static File getProgramFiles_x86()
	{
		return getenvFile("ProgramFiles(x86)");
	}

	private static File getenvFile(String name)
	{
		return new File(System.getenv(name));
	}

}
