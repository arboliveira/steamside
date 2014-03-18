package br.com.arbo.steamside.cloud.autoupload;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.cloud.Dontpad;
import br.com.arbo.steamside.cloud.Uploader;
import br.com.arbo.steamside.settings.file.Save;
import br.com.arbo.steamside.settings.file.Save.Listener;

public class AutouploadingSteamsideData implements Listener {

	private static String read(File file) {
		try {
			return FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	public AutouploadingSteamsideData(Save save, Uploader uploader) {
		this.uploader = uploader;
		this.executor = newSingleDaemonThread();
		save.addListener(this);
	}

	@Override
	public void onSave(File file) {
		executor.submit(() -> upload(file));
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors.newFixedThreadPool(
				1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private void upload(File file) {
		String content = read(file);
		uploader.upload(content);
		new Dontpad().post(content);
	}

	private final ExecutorService executor;

	private final Uploader uploader;

}
