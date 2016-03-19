package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

public final class ExampleDumpVdfStructureFrom_sharedconfig_vdf
{

	public static void main(final String[] args) throws Exception
	{
		new DumpVdfStructureFrom_sharedconfig_vdf(
			File_sharedconfig_vdf_Factory.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}

}