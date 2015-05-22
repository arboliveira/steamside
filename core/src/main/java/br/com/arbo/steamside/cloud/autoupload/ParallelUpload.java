package br.com.arbo.steamside.cloud.autoupload;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.cloud.Uploader;

public class ParallelUpload {

	@Inject
	public ParallelUpload(Uploader uploader)
	{
		this.uploader = uploader;
		this.executor = newSingleDaemonThread();
	}

	void submit(File file)
	{
		// TODO Send Warning to User Alert Bus: Missing, Unavailable
		executor.submit(() -> uploader.upload(file));
	}

	private ExecutorService newSingleDaemonThread()
	{
		return Executors.newFixedThreadPool(
			1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private final ExecutorService executor;

	private final Uploader uploader;

}
