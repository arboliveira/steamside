package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import javax.inject.Inject;

public class Factory_sharedconfig_vdf
		implements DataFactory_sharedconfig_vdf {

	private final File_sharedconfig_vdf file_sharedconfig_vdf;

	@Inject
	public Factory_sharedconfig_vdf(
			File_sharedconfig_vdf file_sharedconfig_vdf) {
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	private static Parse_sharedconfig_vdf newParse_sharedconfig_vdf(
			final File file)
	{
		return new Parse_sharedconfig_vdf(file);
	}

	@Override
	public Data_sharedconfig_vdf data() {
		final File file = file_sharedconfig_vdf.sharedconfig_vdf();
		final Parse_sharedconfig_vdf parser = newParse_sharedconfig_vdf(file);
		return parser.parse();
	}
}
