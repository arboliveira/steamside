package br.com.arbo.steamside.container;

import java.io.File;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class SharedConfigConsume {

	public SharedConfigConsume() {
		this.monitor =
				new FileMonitor<Data_sharedconfig_vdf>(
						new SharedConfigMonitorable());
	}

	public Data_sharedconfig_vdf data() {
		return monitor.getDataFromFuture();
	}

	static class SharedConfigMonitorable implements
			MonitorableFile<Data_sharedconfig_vdf> {

		@Override
		public File file() {
			return File_sharedconfig_vdf.sharedconfig_vdf();
		}

		@Override
		public Data_sharedconfig_vdf data() {
			return Factory_sharedconfig_vdf.fromFile();
		}

	}

	private final FileMonitor<Data_sharedconfig_vdf> monitor;

}
