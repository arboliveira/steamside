package br.com.arbo.steamside.container;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.java.util.concurrent.FutureUtils;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class SharedConfigConsume {

	public SharedConfigConsume() {
		this.executor = newSingleDaemonThread();
		putDataIntoFuture();
		startMonitoringFile();
	}

	public Data_sharedconfig_vdf data() {
		return getDataFromFuture();
	}

	/* ================================================================== */

	private ExecutorService newSingleDaemonThread() {
		return Executors
				.newFixedThreadPool(1, new DaemonThreadFactory(this));
	}

	void putDataIntoFuture() {
		this.state = this.executor.submit(new Consume());
	}

	class Consume implements Callable<Data_sharedconfig_vdf> {

		@Override
		public Data_sharedconfig_vdf call() throws Exception {
			return Factory_sharedconfig_vdf.fromFile();
		}

	}

	private Data_sharedconfig_vdf getDataFromFuture() {
		return FutureUtils.get(state);
	}

	private void startMonitoringFile() {
		final DefaultFileMonitor monitor =
				new DefaultFileMonitor(new VdfChanged());
		monitor.addFile(toFileObject(File_sharedconfig_vdf.sharedconfig_vdf()));
		monitor.start();
	}

	private static FileObject toFileObject(final File vdf) {
		try {
			return VFS.getManager().toFileObject(vdf);
		} catch (final FileSystemException e) {
			throw new RuntimeException(e);
		}
	}

	class VdfChanged implements FileListener {

		@Override
		public void fileCreated(final FileChangeEvent event) {
			// never happens
		}

		@Override
		public void fileDeleted(final FileChangeEvent event) {
			// never happens
		}

		@Override
		public void fileChanged(final FileChangeEvent event) {
			putDataIntoFuture();
		}

	}

	private final ExecutorService executor;
	private Future<Data_sharedconfig_vdf> state;

}
