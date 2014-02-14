package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import br.com.arbo.steamside.apps.Apps;

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
		try {
			return new Parse_sharedconfig_vdf(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Data_sharedconfig_vdf data() {
		final File file = file_sharedconfig_vdf.sharedconfig_vdf();
		final Parse_sharedconfig_vdf parser = newParse_sharedconfig_vdf(file);
		Apps parse_apps_region = parser.parse();
		final Data_sharedconfig_vdf data =
				new Data_sharedconfig_vdf();
		data.apps = parse_apps_region;
		return data;

	}

	public static class FileNotFound_sharedconfig_vdf extends RuntimeException {

		public FileNotFound_sharedconfig_vdf(final Throwable e) {
			super(e);
		}

	}
}
