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

public class FileMonitor<T> {

	public FileMonitor(final MonitorableFile<T> monitorable) {
		this.monitorable = monitorable;
		this.executor = newSingleDaemonThread();
		putDataIntoFuture();
		startMonitoringFile();
	}

	public T getDataFromFuture() {
		return FutureUtils.get(state);
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors
				.newFixedThreadPool(1, new DaemonThreadFactory(this));
	}

	private void startMonitoringFile() {
		final DefaultFileMonitor monitor =
				new DefaultFileMonitor(new VdfChanged());
		monitor.addFile(toFileObject(monitorable.file()));
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

	void putDataIntoFuture() {
		this.state = this.executor.submit(new Consume());
	}

	class Consume implements Callable<T> {

		@Override
		public T call() throws Exception {
			return consume();
		}

	}

	T consume() {
		return monitorable.data();
	}

	private final MonitorableFile<T> monitorable;
	private final ExecutorService executor;
	private Future<T> state;

}