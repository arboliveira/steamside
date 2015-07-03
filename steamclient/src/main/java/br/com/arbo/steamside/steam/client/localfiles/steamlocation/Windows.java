package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.opersys.userhome.ProgramFiles;

public class Windows implements SteamLocation
{

	@Inject
	public Windows(ProgramFiles _ProgramFiles)
	{
		this._ProgramFiles = _ProgramFiles;
	}

	@Override
	public File steam()
	{
		return new File(parent(), "Steam");
	}

	private File parent()
	{
		try
		{
			return _ProgramFiles.getProgramFiles_x86();
		}
		catch (NullPointerException ex)
		{
			return _ProgramFiles.getProgramFiles();
		}
	}

	private final ProgramFiles _ProgramFiles;
}
