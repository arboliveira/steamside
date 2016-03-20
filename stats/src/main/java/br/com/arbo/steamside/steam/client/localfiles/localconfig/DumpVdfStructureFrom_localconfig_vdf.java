package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;

public class DumpVdfStructureFrom_localconfig_vdf
{

	public void dump(Consumer<String> print)
	{
		try (FileInputStream in =
			new FileInputStream(from_localconfig_vdf()))
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

	private File from_localconfig_vdf()
	{
		return file_localconfig_vdf.localconfig_vdf();
	}

	@Inject
	public DumpVdfStructureFrom_localconfig_vdf(
		File_localconfig_vdf file_localconfig_vdf)
	{
		this.file_localconfig_vdf = file_localconfig_vdf;
	}

	private final File_localconfig_vdf file_localconfig_vdf;

}