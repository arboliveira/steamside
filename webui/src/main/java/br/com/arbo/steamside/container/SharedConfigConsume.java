package br.com.arbo.steamside.container;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DataFactory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class SharedConfigConsume implements DataFactory_sharedconfig_vdf,
		MonitorableFile<Data_sharedconfig_vdf> {

	@Inject
	public SharedConfigConsume(
			final File_sharedconfig_vdf file_sharedconfig_vdf,
			final Factory_sharedconfig_vdf factory_sharedconfig_vdf) {
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
		this.factory_sharedconfig_vdf = factory_sharedconfig_vdf;
		this.monitor =
				new FileMonitor<Data_sharedconfig_vdf>(this);
	}

	final File_sharedconfig_vdf file_sharedconfig_vdf;
	final Factory_sharedconfig_vdf factory_sharedconfig_vdf;

	@Override
	public Data_sharedconfig_vdf data() {
		return monitor.getDataFromFuture();
	}

	@Override
	public File file() {
		return file_sharedconfig_vdf.sharedconfig_vdf();
	}

	@Override
	public Data_sharedconfig_vdf read() {
		return factory_sharedconfig_vdf.data();
	}

	private final FileMonitor<Data_sharedconfig_vdf> monitor;

}
