package br.com.arbo.steamside.steam.client.localfiles.steamlocation;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.opersys.userhome.ProgramFiles;

public class Windows implements SteamLocation {

	private final ProgramFiles _ProgramFiles;

	@Inject
	public Windows(ProgramFiles _ProgramFiles) {
		this._ProgramFiles = _ProgramFiles;
	}

	@Override
	public File steam() {
		return new File(parent(), "Steam");
	}

	private File parent() {
		return _ProgramFiles.getProgramFiles();
	}
}
