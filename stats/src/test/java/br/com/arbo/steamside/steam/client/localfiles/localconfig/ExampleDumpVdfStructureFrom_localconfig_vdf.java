package br.com.arbo.steamside.steam.client.localfiles.localconfig;

public final class ExampleDumpVdfStructureFrom_localconfig_vdf
{

	public static void main(final String[] args) throws Exception
	{
		new DumpVdfStructureFrom_localconfig_vdf(
			File_localconfig_vdf_Factory.fromSteamPhysicalFiles())
				.dump(System.out::println);
	}

}