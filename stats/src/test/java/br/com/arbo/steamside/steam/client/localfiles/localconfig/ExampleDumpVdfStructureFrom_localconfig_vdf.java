package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;

public final class ExampleDumpVdfStructureFrom_localconfig_vdf
{

	public static void main(final String[] args) throws Exception
	{
		new DumpVdfStructureFrom_localconfig_vdf(
			new File_localconfig_vdf(
				Dirs_userid.fromSteamPhysicalFiles()))
					.dump(System.out::println);
	}

}