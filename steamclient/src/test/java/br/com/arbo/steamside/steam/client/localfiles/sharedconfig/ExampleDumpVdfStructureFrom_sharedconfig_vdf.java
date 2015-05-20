package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf {

	public static void main(final String[] args) throws FileNotFoundException
	{
		DumpVdfStructure
			.dump(
				new FileInputStream(
					from_sharedconfig_vdf().sharedconfig_vdf()));
	}

	private static File_sharedconfig_vdf from_sharedconfig_vdf()
	{
		return new File_sharedconfig_vdf(
			Dirs_userid.fromSteamPhysicalFiles());
	}

}