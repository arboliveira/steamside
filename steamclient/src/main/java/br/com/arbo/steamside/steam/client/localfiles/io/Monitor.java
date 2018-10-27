package br.com.arbo.steamside.steam.client.localfiles.io;

import java.io.File;
import java.util.stream.Stream;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

public class Monitor
{

	class FileChanged implements FileListener
	{

		@Override
		public void fileChanged(final FileChangeEvent event)
		{
			listener.run();
		}

		@Override
		public void fileCreated(final FileChangeEvent event)
		{
			// never happens
		}

		@Override
		public void fileDeleted(final FileChangeEvent event)
		{
			// never happens
		}

	}

	public void start()
	{
		monitor.start();
	}

	public void stop()
	{
		monitor.stop();
	}

	public Monitor(Stream<File> files, Runnable listener)
	{
		this.listener = listener;
		this.monitor = createMonitor(files);
	}

	final Runnable listener;

	private static FileObject toFileObject(final File file)
	{
		try
		{
			return VFS.getManager().toFileObject(file);
		}
		catch (final FileSystemException e)
		{
			throw new RuntimeException(e);
		}
	}

	private DefaultFileMonitor createMonitor(Stream<File> files)
	{
		DefaultFileMonitor monitor = new DefaultFileMonitor(new FileChanged());
		files
			.map(Monitor::toFileObject)
			.forEach(monitor::addFile);
		return monitor;
	}

	private final DefaultFileMonitor monitor;

}