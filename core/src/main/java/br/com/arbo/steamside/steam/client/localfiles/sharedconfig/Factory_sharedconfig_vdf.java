package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

public class Factory_sharedconfig_vdf
		implements DataFactory_sharedconfig_vdf {

	private final File_sharedconfig_vdf file_sharedconfig_vdf;

	@Inject
	public Factory_sharedconfig_vdf(
			File_sharedconfig_vdf file_sharedconfig_vdf) {
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	@Override
	public R_apps apps() {
		final String content =
				readFileToString(file_sharedconfig_vdf.sharedconfig_vdf());
		return new Parse_sharedconfig_vdf(content).apps();
	}

	@Override
	public Data_sharedconfig_vdf data() {
		final String content =
				readFileToString(file_sharedconfig_vdf.sharedconfig_vdf());
		final Data_sharedconfig_vdf parse =
				new Parse_sharedconfig_vdf(content).parse();
		return parse;
	}

	public static class FileNotFound_sharedconfig_vdf extends RuntimeException {

		public FileNotFound_sharedconfig_vdf(final Throwable e) {
			super(e);
		}

	}

	private static String readFileToString(final File from) {
		try {
			return FileUtils.readFileToString(from);
		} catch (final FileNotFoundException e) {
			throw new FileNotFound_sharedconfig_vdf(e);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
