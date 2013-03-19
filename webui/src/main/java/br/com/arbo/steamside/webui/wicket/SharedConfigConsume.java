package br.com.arbo.steamside.webui.wicket;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Data_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Factory_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class SharedConfigConsume {

	public Data_sharedconfig_vdf data() {
		try {
			return state.get();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final ExecutionException e) {
			final Throwable cause = e.getCause();
			if (cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			throw new RuntimeException(e);
		}
	}

	public SharedConfigConsume() {
		this.executor = Executors.newFixedThreadPool(
				1, new DaemonThreadFactory(this));
		consume();
		startMonitoring();
	}

	void consume() {
		this.state = this.executor.submit(new Consume());
	}

	class Consume implements Callable<Data_sharedconfig_vdf> {

		@Override
		public Data_sharedconfig_vdf call() throws Exception {
			return Factory_sharedconfig_vdf.fromFile();
		}

	}

	private void startMonitoring() {
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
			consume();
		}

	}

	private final ExecutorService executor;
	private Future<Data_sharedconfig_vdf> state;

}
