package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf {

	public static void main(final String[] args) throws FileNotFoundException
	{
		DumpVdfStructure
				.dump(
				new FileInputStream(
						from_sharedconfig_vdf().sharedconfig_vdf()));
	}

	private static Dir_userid from_Dir_userid()
	{
		return new Dir_userid(
				new Dir_userdata(
						SteamLocations
								.fromSteamPhysicalFiles()));
	}

	private static File_sharedconfig_vdf from_sharedconfig_vdf()
	{
		return new File_sharedconfig_vdf(
				from_Dir_userid());
	}

}