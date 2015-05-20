package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_localconfig_vdf {

	public static void main(final String[] args) throws FileNotFoundException
	{
		DumpVdfStructure.dump(new FileInputStream(from_localconfig_vdf()));
	}

	private static File from_localconfig_vdf()
	{
		final File_localconfig_vdf vdf = new File_localconfig_vdf(
			Dirs_userid.fromSteamPhysicalFiles());
		return vdf.localconfig_vdf();
	}

}