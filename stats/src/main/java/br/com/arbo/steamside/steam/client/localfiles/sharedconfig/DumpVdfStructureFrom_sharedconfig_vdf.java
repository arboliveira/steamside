package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;

public class DumpVdfStructureFrom_sharedconfig_vdf
{

	public void dump(Consumer<String> print)
	{
		try (FileInputStream in =
			new FileInputStream(file_sharedconfig_vdf.sharedconfig_vdf()))
		{
			DumpVdfStructure.dump(in, print);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	public DumpVdfStructureFrom_sharedconfig_vdf(
		File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	private final File_sharedconfig_vdf file_sharedconfig_vdf;

}