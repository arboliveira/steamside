package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf
{

	public static void main(final String[] args) throws Exception
	{
		new DumpVdfStructureFrom_sharedconfig_vdf(
			new File_sharedconfig_vdf(
				Dirs_userid.fromSteamPhysicalFiles()))
					.dump(System.out::println);
	}

}