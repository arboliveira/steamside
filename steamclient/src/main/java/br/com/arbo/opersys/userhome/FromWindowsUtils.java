package br.com.arbo.opersys.userhome;

import java.io.File;

import br.com.arbo.opersys.windows.WindowsUtils;

public class FromWindowsUtils implements ProgramFiles
{

	@Override
	public File getProgramFiles()
	{
		return WindowsUtils.getProgramFiles();
	}

	@Override
	public File getProgramFiles_x86()
	{
		return WindowsUtils.getProgramFiles_x86();
	}

}
