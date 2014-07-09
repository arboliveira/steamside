package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;

public final class ExampleDumpVdfStructureFrom_localconfig_vdf {

	public static void main(final String[] args) throws FileNotFoundException
	{
		DumpVdfStructure.dump(new FileInputStream(from_localconfig_vdf()));
	}

	private static Dir_userid from_Dir_userid()
	{
		return new Dir_userid(
				new Dir_userdata(
						SteamLocations
								.fromSteamPhysicalFiles()));
	}

	private static File from_localconfig_vdf()
	{
		final File_localconfig_vdf vdf = new File_localconfig_vdf(
				from_Dir_userid());
		return vdf.localconfig_vdf();
	}

}