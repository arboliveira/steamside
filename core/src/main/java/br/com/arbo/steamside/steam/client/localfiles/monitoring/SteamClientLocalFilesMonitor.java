package br.com.arbo.steamside.steam.client.localfiles.monitoring;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;

public class SteamClientLocalFilesMonitor {

	public SteamClientLocalFilesMonitor(
			File_localconfig_vdf localconfig_vdf,
			File_sharedconfig_vdf sharedconfig_vdf,
			File_appinfo_vdf appinfo_vdf) {
		monitor = new DefaultFileMonitor(new FileChanged());
		this.sharedconfig_vdf =
				toFileObject(sharedconfig_vdf.sharedconfig_vdf());
		this.localconfig_vdf =
				toFileObject(localconfig_vdf.localconfig_vdf());
		this.appinfo_vdf =
				toFileObject(appinfo_vdf.appinfo_vdf());

		for (FileObject file : Arrays.asList(this.sharedconfig_vdf,
				this.localconfig_vdf, this.appinfo_vdf))
			monitor.addFile(file);
	}

	public void start() {
		monitor.start();
	}

	public void stop() {
		monitor.stop();
	}

	class FileChanged implements FileListener {

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
			changes.add(event.getFile());
		}

	}

	private static FileObject toFileObject(final File file) {
		try {
			return VFS.getManager().toFileObject(file);
		} catch (final FileSystemException e) {
			throw new RuntimeException(e);
		}
	}

	private final FileObject sharedconfig_vdf;
	private final FileObject localconfig_vdf;
	private final FileObject appinfo_vdf;
	private final DefaultFileMonitor monitor;
	final Changes changes = new Changes();

	static class Changes {

		private final HashSet<FileObject> set = new HashSet<FileObject>(3);

		synchronized void add(FileObject file) {
			set.add(file);
		}

	}
}
