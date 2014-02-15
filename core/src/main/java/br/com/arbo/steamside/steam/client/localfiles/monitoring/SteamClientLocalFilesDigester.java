package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.springframework.context.Lifecycle;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Parse_sharedconfig_vdf;

public class SteamClientLocalFilesDigester
		implements Lifecycle, SteamClientLocalFilesChangeListener {

	private final File_sharedconfig_vdf file_sharedconfig_vdf;

	@Inject
	public SteamClientLocalFilesDigester(
			File_sharedconfig_vdf file_sharedconfig_vdf) {
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
	}

	@Override
	public void start() {
		digestInParallel();
	}

	@Override
	public void stop() {
		executor.shutdown();
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public void fileChanged() {
		digestInParallel();
	}

	private void digestInParallel() {
		executor.execute(new Digest());
	}

	class Digest implements Runnable {

		@Override
		public void run() {
			digestXT();
		}
	}

	private final ExecutorService executor = Executors
			.newSingleThreadExecutor();

	void digestXT() {
		digest();
	}

	private void digest() {
		final File file = file_sharedconfig_vdf.sharedconfig_vdf();
		final Parse_sharedconfig_vdf parser = new Parse_sharedconfig_vdf(file);
		Data_sharedconfig_vdf data = parser.parse();
	}
}
